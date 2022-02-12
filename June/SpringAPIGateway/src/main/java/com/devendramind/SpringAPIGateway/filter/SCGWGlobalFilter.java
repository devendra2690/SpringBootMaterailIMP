package com.devendramind.SpringAPIGateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SCGWGlobalFilter extends AbstractGatewayFilterFactory<SCGWGlobalFilter.Config> {

    public static final String RequestIdHeader = "X-RS-RequestId";


    public SCGWGlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        System.out.println("inside SCGWGlobalFilter.apply method");

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest().mutate().header(RequestIdHeader, UUID.randomUUID().toString()).build();
            exchange.getResponse().getHeaders().set(RequestIdHeader,request.getHeaders().get(RequestIdHeader).get(0));
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}