package com.devendrabrain.OrderManagement.service.impl;

import com.devendrabrain.OrderManagement.bo.OrderBO;
import com.devendrabrain.OrderManagement.constant.AMQPKeys;
import com.devendrabrain.OrderManagement.dao.OrderDAO;
import com.devendrabrain.OrderManagement.dto.NotificationDTO;
import com.devendrabrain.OrderManagement.entity.Order;
import com.devendrabrain.OrderManagement.entity.OrderDetails;
import com.devendrabrain.OrderManagement.publisher.NotificationQueuePublisher;
import com.devendrabrain.OrderManagement.service.OrderService;
import com.devendrabrain.OrderManagement.transformer.NotificationTransfomer;
import com.devendrabrain.OrderManagement.transformer.OrderTransformer;
import com.devendrabrain.OrderManagement.util.billing.BillingDTO;
import com.devendrabrain.OrderManagement.util.billing.BillingServiceUtil;
import com.devendrabrain.OrderManagement.util.customermgr.CustomerDTO;
import com.devendrabrain.OrderManagement.util.customermgr.CustomerServiceUtil;
import com.devendrabrain.OrderManagement.util.inventory.InventoryDTO;
import com.devendrabrain.OrderManagement.util.inventory.InventoryServiceUtil;
import com.devendrabrain.OrderManagement.util.notification.pubsub.PubSubServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl  implements OrderService {

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    NotificationQueuePublisher notificationMgrPublisher;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public List<OrderBO> findAll() {


        //fetch call
        List<Order> orderList = orderDAO.fetchAll();

        //Transform
        List<OrderBO> orderBOList = new ArrayList<>();
        for(Order order : orderList) {

            OrderBO orderBO = OrderTransformer.tranformOrderToOrderBO(order,new OrderBO());
            orderBOList.add(orderBO);
        }

        return orderBOList;
    }

    @Override
    public OrderBO fetchById(Long orderId) {

        // Validatw INputa

        //Fetch call
        Order order = orderDAO.fetchById(orderId);

        //Teansform
        OrderBO orderBO = new OrderBO();
        if(order != null) {
            OrderTransformer.tranformOrderToOrderBO(order,orderBO);
        }

        return orderBO;
    }

    @Override
    public OrderBO create(OrderBO orderBO) {

        // Validate inputs

        Order order = getOrder(orderBO);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<CustomerDTO> customerDTOCallable = new Callable<CustomerDTO>() {
            @Override
            public CustomerDTO call() throws Exception {
                return getCustomerRecords(order);
            }
        };

        Callable<Map<Long, String>> mapCallable = new Callable<Map<Long, String>>() {
            @Override
            public Map<Long, String> call() throws Exception {
                return getInventoryRecords(order);
            }
        };

        CompletableFuture<CustomerDTO> customerDTOCompletableFuture = CompletableFuture.supplyAsync(() -> {
            CustomerDTO customerDTOResponse = getCustomerRecords(order);
            return customerDTOResponse;
        });

        CompletableFuture<Map<Long, String>> inventoryMapDTOCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Map<Long, String> inventoryDetailsMap = getInventoryRecords(order);
            return inventoryDetailsMap;
        });

        CustomerDTO customerDTO = null;
        Map<Long, String> inventoryDetails = null;
        try {
            customerDTO = customerDTOCompletableFuture.get();
            inventoryDetails = inventoryMapDTOCompletableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        CompletableFuture<Void> processBillingRecords = CompletableFuture.supplyAsync(()->{
            //Process Billing
            processBillingRecords(order);
            return null;
        });

        CustomerDTO finalCustomerDTO = customerDTO;
        Map<Long, String> finalInventoryDetails = inventoryDetails;
        CompletableFuture<Void> sendNotification = CompletableFuture.supplyAsync(()->{
            //Using RabbitMQ
            sendNotification(order, finalCustomerDTO, finalInventoryDetails);
            return null;
        });


        CompletableFuture<Void> sendNotificationUsingAWSSQS = CompletableFuture.supplyAsync(()->{
            //Using AWS SQS
            sendNotificationUsingAWSSQS(order, finalCustomerDTO, finalInventoryDetails);
            return null;
        });

        CompletableFuture<Void> sendNotificationUsingPubSub = CompletableFuture.supplyAsync(()->{

            //Send Pubsub messages to Sales and packaging team
            sendNotificationUsingPubSub(order, finalCustomerDTO, finalInventoryDetails);
            return null;
        });

        //transform
        OrderTransformer.tranformOrderToOrderBO(order,orderBO);

        return orderBO;
    }

    private Order getOrder(OrderBO orderBO) {
        //transform
        Order order = OrderTransformer.tranformOrderBOToOrder(orderBO, new Order());

        // In One-to-Many or Many-To-One Hibernate never set foreign key to your Child table automatically
        // We need to tell Hibernate to set it like below
        // Order is Parent class and OrderDetails is child class
        // we have to explicitly set Order object inside OrderDetails  List
        // If we do not do this foreign key will be inserted as null
        List<OrderDetails> orderDetails = order.getOrderDetailsList().stream().map(orderDetailObj -> {
            orderDetailObj.setOrder(order);
            return orderDetailObj;
        }).collect(Collectors.toList());

        order.getOrderDetailsList().clear();
        order.setOrderDetailsList(orderDetails);

        // save call
        orderDAO.create(order);
        return order;
    }

    @Override
    public void delete(Long orderId) {

        //validate input

        //delete call
        orderDAO.delete(orderId);
    }

    private void processBillingRecords(Order order) {

        BillingServiceUtil billingServiceUtil = new BillingServiceUtil();
        BillingDTO billingDTO = OrderTransformer.transformOrderToBillingDTO(order, new BillingDTO());

        // Process Billing
        ResponseEntity<BillingDTO> billingDTOResponseEntity = billingServiceUtil.processBillingRecords(billingDTO);
        BillingDTO billingDTOResonseObj = billingDTOResponseEntity.getBody();
        if(billingDTOResonseObj == null ) {
            // process it to Rabbit MQ for further processing using Kaftka
        }
    }

    private CustomerDTO getCustomerRecords(Order order) {

        //Fetch CustomerDetails
        CustomerServiceUtil customerServiceUtil = new CustomerServiceUtil();
        ResponseEntity<CustomerDTO> customerDTOResponseEntity = customerServiceUtil.fetchCustomerRecords(order.getCustomerId());
        CustomerDTO customerDTO = null;
        if(customerDTOResponseEntity != null && customerDTOResponseEntity.getBody() != null) {
            customerDTO = customerDTOResponseEntity.getBody();
        }

        return customerDTO;
    }

    private Map<Long,String> getInventoryRecords(Order order) {

        //Fetch Inventory
        InventoryServiceUtil inventoryServiceUtil = new InventoryServiceUtil();
        Map<Long,String> inventoryDetails = new HashMap<>();

        for(OrderDetails orderDetails : order.getOrderDetailsList()) {

            // Rewrite this to fetch all records at once that way we can save network and DB call
            ResponseEntity<InventoryDTO> customerDTOResponseEntity = inventoryServiceUtil.fetchInventoryRecords(orderDetails.getInventoryId());
            InventoryDTO inventoryDTO = null;
            if(customerDTOResponseEntity != null && customerDTOResponseEntity.getBody() != null) {
                inventoryDTO = customerDTOResponseEntity.getBody();
                inventoryDetails.put(inventoryDTO.getInventoryId(),inventoryDTO.getInventoryName());
            }
        }

        return inventoryDetails;
    }

    private void sendNotification(Order order, CustomerDTO customerDTO, Map<Long, String> inventoryDetails) {
        //Transform NotificationDTOObject
        NotificationDTO notificationDTO = NotificationTransfomer.transformNotificationDTO(order, customerDTO, inventoryDetails);
        // Async Call to NotifyMgr and EventMgr
        notificationMgrPublisher.publish(notificationDTO, AMQPKeys.EXHANGE,AMQPKeys.ROUNTING_KEY);
    }


    private void sendNotificationUsingAWSSQS(Order order, CustomerDTO customerDTO, Map<Long, String> inventoryDetails) {
        //Transform NotificationDTOObject
        NotificationDTO notificationDTO = NotificationTransfomer.transformNotificationDTO(order, customerDTO, inventoryDetails);
        queueMessagingTemplate.convertAndSend(endpoint, notificationDTO);
    }

    private void sendNotificationUsingPubSub(Order order, CustomerDTO customerDTO, Map<Long, String> inventoryDetails) {

        //Transform NotificationDTOObject
        NotificationDTO notificationDTO = NotificationTransfomer.transformNotificationDTO(order, customerDTO, inventoryDetails);

        PubSubServiceUtil pubSubServiceUtil = new PubSubServiceUtil();
        pubSubServiceUtil.publishNotification(notificationDTO);
    }
}
