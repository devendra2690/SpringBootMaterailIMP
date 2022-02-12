package com.devendrabrain.OrderManagement.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationQueuePublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;


    public void publish(Object object, String exchange, String bindingBuilder) {

        if (object != null) {
            /*
             * Send message to RabbitMQ
             */
            try {
                rabbitTemplate.convertAndSend(exchange, bindingBuilder, object);
            } catch (Exception exc) {

            }

        } else {

        }
    }
}
