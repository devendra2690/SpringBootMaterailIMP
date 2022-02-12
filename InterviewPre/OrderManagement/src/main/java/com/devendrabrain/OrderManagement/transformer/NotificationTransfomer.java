package com.devendrabrain.OrderManagement.transformer;

import com.devendrabrain.OrderManagement.dto.NotificationDTO;
import com.devendrabrain.OrderManagement.entity.Order;
import com.devendrabrain.OrderManagement.util.customermgr.CustomerDTO;

import java.util.Map;

public class NotificationTransfomer {

    public static NotificationDTO transformNotificationDTO(Order order, CustomerDTO customerDTO, Map<Long, String> inventoryDetails) {

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setCustomerId(order.getCustomerId());
        notificationDTO.setOrderId(order.getOrderId());
        notificationDTO.setTotalPrice(order.getTotalAmount());
        notificationDTO.setInventoryDetails(inventoryDetails);
        notificationDTO.setEmail(customerDTO.getEmailId());
        notificationDTO.setCustomerFirstName(customerDTO.getFirstName());
        notificationDTO.setCustomerLastName(customerDTO.getLastName());

        return notificationDTO;
    }
}
