package top.jbzm.spring.demo.springcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class SpringCloudGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudGatewayApplication.class, args);
  }

  @Bean
  public RouteLocator coustomRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
    return routeLocatorBuilder
        .routes()
        // basic proxy
        .route(r -> r.path("/bd").uri("http://baidu.com:80/").id("bd"))
        .build();
  }

  @Bean
  public RouteLocator testRouteLocator(RouteLocatorBuilder builder) {
    return builder
        .routes()
        .route(
            "rewritepath_route",
            r ->
                r.path("/foo/**")
                    .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/$\\{segment}"))
                    .uri("http://www.baidu.com"))
        .build();
  }

//  @Bean
//  public RouteLocator retryRouteLocator1(RouteLocatorBuilder routeLocatorBuilder) {
//    return routeLocatorBuilder
//        .routes()
//        .route(
//            "retry_route",
//            r ->
//                r.path("/test/retry")
//                    .filters(f -> f.retry(c -> c.setRetries(2).setStatuses(HttpStatus.BAD_REQUEST)))
//                    .uri("http://localhost:8081/test/retry?key=lol&count=3"))
//        .build();
//  }

  @Bean
  public RouteLocator retryRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("retry_route", r -> r.path("/test/retry")
                    .filters(f ->f.retry(config -> config.setRetries(2).setStatuses(HttpStatus.INTERNAL_SERVER_ERROR)))
                    .uri("http://localhost:8071/retry?key=abc&count=2"))
            .build();
  }

  //  @Bean
  //  public RouteLocator addPrefixRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
  //    return routeLocatorBuilder
  //        .routes()
  //        .route(
  //            "add_prefix",
  //            r ->
  //                r.path("/test/retry")
  //                    .filters(f -> f.prefixPath("test"))
  //                    .uri("http://localhost:8080/test/retry?key=lol&count=3"))
  //        .build();
  //  }
}
