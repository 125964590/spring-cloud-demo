package com.talbrain.vegas.gatewayservicenew.config;

import com.talbrain.vegas.gatewayservicenew.tool.handler.FallBackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * @author jbzm
 * @date 2019-06-25 00:31
 */
@Configuration
public class RouteFunctionConfig {

  private final FallBackHandler fallBackHandler;

    public RouteFunctionConfig(FallBackHandler fallBackHandler) {
        this.fallBackHandler = fallBackHandler;
    }

    @Bean
  public RouterFunction routerFunction() {
    return RouterFunctions.route(RequestPredicates.GET("/fallback"), fallBackHandler);
  }
}
