package com.devendrabrain.NotificationManager.listner;

import com.devendrabrain.NotificationManager.constant.AMQPKeys;
import com.devendrabrain.NotificationManager.dto.NotificationDTO;
import com.devendrabrain.NotificationManager.service.impl.EmailService;
import com.devendrabrain.NotificationManager.util.JSONUtils;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationQueueListner implements MessageListener {


    @Autowired
    EmailService emailService;

    /*


    @RabbitListener(queues = AMQPKeys.QUEUE)
    public void consumerNotification(Message message) {
    }*/

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        System.out.println(message);
        NotificationDTO notificationDTO = null;
        String json = new String(message.getBody());

        if(json != null) {

            notificationDTO = JSONUtils.toObject(json,NotificationDTO.class);
        }

        emailService.sendEmail(notificationDTO);
    }
}
