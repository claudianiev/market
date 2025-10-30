package com.market.gateway.bean;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;

@Configuration
@AllArgsConstructor
public class GatewayBeans {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> route
                        .path("/ms-productos/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8083")
                )
                .route(route -> route
                        .path("/ms-inventario/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8082")
                )
                .build();
    }
}