package com.talbrain.vegas.gatewayservicenew.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author jbzm
 * @date 2019-06-21 11:48
 */
public class GatewayRateLimitFilterByIp implements GatewayFilter, Ordered {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    chain.filter(exchange);
    return null;
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
