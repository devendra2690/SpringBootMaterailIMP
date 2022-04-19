package com.devendrabrain.InventoryGateway.config;

import com.devendrabrain.InventoryGateway.LoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {

    @Autowired
    LoggingFilter loggingFilter;


      // Hostname used here should match with service name defined in docker-compose file

      @Bean
      public RouteLocator routeLocator (RouteLocatorBuilder routeLocatorBuilder) {

          return routeLocatorBuilder.routes().
                  route(predicateSpec -> predicateSpec
                          .path("/BillingMgr/**")
                          .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(loggingFilter)
                          ).uri("http://billingmangement:8381/BillingMgr/")
                  ).
                  route(predicateSpec -> predicateSpec
                          .path("/CustomerMgr/circuitBreaker")
                          .filters(gatewayFilterSpec ->
                                  gatewayFilterSpec.circuitBreaker(
                                          config -> config.setFallbackUri("/fallback")))
                          .uri("http://customermanagment:8081/CustomerMgr/")
                  ).
                  route(predicateSpec -> predicateSpec
                          .path("/CustomerMgr/**")
                          .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(loggingFilter)
                          ).uri("http://customermanagment:8081/CustomerMgr/")
                  ).
                  route(predicateSpec -> predicateSpec
                          .path("/OrderMgr/**")
                          .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(loggingFilter)
                          ).uri("http://ordermanagement:8281/OrderMgr/")
                  ).
                  route(predicateSpec -> predicateSpec
                          .path("/InventoryMgr/**")
                          .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(loggingFilter)
                          ).uri("http://inventorymanagement:8181/InventoryMgr/")
                  ).
                  route(predicateSpec -> predicateSpec
                        .path("/AWSSNSMgr/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(loggingFilter)
                        ).uri("http://ordernotificatopnawspubsub:8781/AWSSNSMgr/")
                 ).
                  build();
      }

}
