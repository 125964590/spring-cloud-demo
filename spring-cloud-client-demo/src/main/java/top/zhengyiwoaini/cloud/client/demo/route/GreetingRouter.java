package top.zhengyiwoaini.cloud.client.demo.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.zhengyiwoaini.cloud.client.demo.handler.GreetingHandler;
import top.zhengyiwoaini.cloud.client.demo.handler.TestHandler;

@Configuration
public class GreetingRouter {

  @Bean
  public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {

    return RouterFunctions.route(
        RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
        greetingHandler::hello);
  }

  @Bean
  public RouterFunction<ServerResponse> routeTest(TestHandler testHandler) {
    return RouterFunctions.route(
            RequestPredicates.GET("/name/{name}").and(RequestPredicates.accept(MediaType.ALL)),
            testHandler::getName)
        .andRoute(
            RequestPredicates.POST("/default").and(RequestPredicates.accept(MediaType.ALL)),
            testHandler::getDefault)
        .andRoute(
            RequestPredicates.POST("/decorator").and(RequestPredicates.accept(MediaType.ALL)),
            testHandler::getDecorator);
  }
}
