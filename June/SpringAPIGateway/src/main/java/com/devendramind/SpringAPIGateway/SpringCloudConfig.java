package com.devendramind.SpringAPIGateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
   /* @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/OrgMgr/**")
                        .uri("lb://SPRING-ORG-MGR"))
                .route(r -> r.path("/StudentMgr/**")
                        .uri("lb://SPRING-STUDENT-MGR"))
                .build();
    }*/
}
