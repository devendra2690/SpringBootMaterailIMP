package com.devendrabrain.StudentMgr.publisher;

import com.devendrabrain.StudentMgr.bo.EventHttpLogBO;
import com.devendrabrain.StudentMgr.constant.AmqType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventHttpPublisher {

    protected static final Logger log = LoggerFactory.getLogger(EventHttpPublisher.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Async
    public void publisher(EventHttpLogBO eventHttpLogBO) {

        if (eventHttpLogBO != null) {

            /*
             * Send message to RabbitMQ
             */

            log.info("EventHttpLogGenerator filter before sending to rabbitmq");
            rabbitTemplate.convertAndSend(AmqType.eventHttpLogDirectExchange.getValue(),
                    AmqType.eventHttpLogBindingBuilder.getValue(), eventHttpLogBO);
            log.info("EventHttpLogGenerator filter after sending to rabbitmq");
        } else {
            log.error(
                    "ClassName:EventHttpLogGenerator, Error Message: EventLogObject is empty not able to send to Rabbitmq");
        }
    }
}
