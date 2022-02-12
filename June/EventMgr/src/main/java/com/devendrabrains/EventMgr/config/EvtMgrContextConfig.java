package com.devendrabrains.EventMgr.config;

import com.devendrabrains.EventMgr.constant.AmqType;
import com.devendrabrains.EventMgr.listner.EventHttpLogMessageListener;
import com.devendrabrains.EventMgr.repository.EventHttpLogRepository;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvtMgrContextConfig {

    @Autowired
    EventHttpLogRepository eventHttpLogRepository;

    @Autowired
    ConnectionFactory connectionFactory;

    /*
     * EventHttpLog message Listener and Container
     */

    @Bean
    public MessageListener eventHttpLogMessageListener() {
        return new EventHttpLogMessageListener(eventHttpLogRepository);
    }

    @Bean
    SimpleMessageListenerContainer eventHttpLogContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(AmqType.eventHttpLogQueue.getValue());
        container.setMessageListener(eventHttpLogMessageListener());
        container.setDefaultRequeueRejected(false);
        return container;
    }

}
