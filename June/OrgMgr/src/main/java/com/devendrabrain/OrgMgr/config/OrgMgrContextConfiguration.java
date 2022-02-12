package com.devendrabrain.OrgMgr.config;

import com.devendrabrain.OrgMgr.constant.AmqType;
import com.devendrabrain.OrgMgr.filter.EventHttpLogFilter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class OrgMgrContextConfiguration implements WebMvcConfigurer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Environment environment;

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingFilter());
    }*/

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(eventHttpLogFilter());
        registration.addUrlPatterns("/*");
        registration.setName(AmqType.eventHttpfilterName.getValue());
        registration.setOrder(10);
        return registration;
    }

    @Bean
    @Order
    public Filter eventHttpLogFilter() {
        return new EventHttpLogFilter(rabbitTemplate, environment);
    }
}
