package com.devendrabrain.StudentMgr.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AmqpConfig {

    public static final String RABBITMQ_BROKER_URL = "rabbitmq.brokerurl";
    public static final String RABBITMQ_USERNAME = "rabbitmq.username";
    public static final String RABBITMQ_PORT = "rabbitmq.port";
    public static final String RABBITMQ_PASSWORD = "rabbitmq.password";


    @Autowired
    private Environment environment;

    @Bean
    public CachingConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                environment.getRequiredProperty(RABBITMQ_BROKER_URL));

        connectionFactory.setUsername(environment.getRequiredProperty(RABBITMQ_USERNAME));
        connectionFactory
                .setPassword(environment.getRequiredProperty(RABBITMQ_PASSWORD));
        connectionFactory.setPort(Integer.parseInt(environment.getRequiredProperty(RABBITMQ_PORT)));

        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

