package com.talbrain.vegas.gatewayservicenew.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author jbzm
 * @date 2019-10-21 15:28
 */
//@Component
public class TestGetBody implements GlobalFilter, Ordered {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String s = resolveBodyFromRequest(exchange.getRequest());
    System.out.println(s);
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return 10000;
  }

  private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
    // 获取请求体
    Flux<DataBuffer> body = serverHttpRequest.getBody();
    StringBuilder sb = new StringBuilder();

    body.subscribe(
        buffer -> {
          byte[] bytes = new byte[buffer.readableByteCount()];
          buffer.read(bytes);
          DataBufferUtils.release(buffer);
          String bodyString = new String(bytes, StandardCharsets.UTF_8);
          sb.append(bodyString);
        });
    return sb.toString();
  }
}
