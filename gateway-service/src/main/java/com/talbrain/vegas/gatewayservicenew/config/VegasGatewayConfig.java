package com.talbrain.vegas.gatewayservicenew.config;

import org.springframework.stereotype.Component;

/**
 * @author jbzm
 * @date 2019-06-11 18:19
 */
@Component
public class VegasGatewayConfig {

//  @Bean
//  public RouteLocator vegasRouteLocator(RouteLocatorBuilder builder) {
//    return builder
//        .routes()
//        .route(
//            "create_task",
//            r ->
//                r.path("/api/create/**")
//                    .filters(f -> f.stripPrefix(2))
//                    .uri("lb://createtask-service"))
//        .route(
//            "auth-service",
//            r -> r.path("/api/auth/**").filters(f -> f.stripPrefix(2)).uri("lb://auth-service"))
//        .route(
//            "vegas-admin-service",
//            r ->
//                r.path("/api/admin/**")
//                    .filters(f -> f.stripPrefix(2))
//                    .uri("lb://vegas-admin-service"))
//        .route(
//            "vegas-platform",
//            r -> r.path("/api/**").filters(f -> f.stripPrefix(1)).uri("lb://vegas-platform"))
//        .build();
//  }
}
