package com.devendrabrain.StudentMgr.config;

import com.devendrabrain.StudentMgr.constant.AmqType;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("connectionFactory")
public class RabbitMQConfiguration {

    @Autowired
    private ConnectionFactory connectionFactory;

    /*
     * HttpEventLog
     */
    @Bean
    public Queue eventHttpLogQueue() {
        return new Queue(AmqType.eventHttpLogQueue.getValue());
    }

    @Bean
    public DirectExchange eventHttpLogDirectExchange() {
        return new DirectExchange(AmqType.eventHttpLogDirectExchange.getValue());
    }

    @Bean
    public Binding eventHttpLogBindingBuilder() {
        return BindingBuilder.bind(eventHttpLogQueue()).to(eventHttpLogDirectExchange())
                .with(AmqType.eventHttpLogBindingBuilder.getValue());
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);

        // Event Http Log Declaration
        amqpAdmin.declareQueue(eventHttpLogQueue());
        amqpAdmin.declareExchange(eventHttpLogDirectExchange());
        amqpAdmin.declareBinding(eventHttpLogBindingBuilder());

        return amqpAdmin;
    }
}
