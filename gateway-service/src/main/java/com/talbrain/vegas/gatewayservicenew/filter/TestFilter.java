package com.talbrain.vegas.gatewayservicenew.filter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author jbzm
 * @date 2019-10-21 14:50
 */
@Slf4j
@Component
public class TestFilter implements GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    ServerHttpResponse response = exchange.getResponse();
    ServerHttpRequest request = exchange.getRequest();
    MultiValueMap<String, String> queryParams = request.getQueryParams();
    if (queryParams.containsKey("testFilter")) {
      return exchange
          .getRequest()
          .getBody()
          .collectList()
          .flatMap(
              dataBuffers -> {
                NettyDataBufferFactory factory = (NettyDataBufferFactory) response.bufferFactory();
                DataBuffer dataBuffer = factory.join(dataBuffers);
                String bodyStr = null;
                try {
                  bodyStr = IOUtils.toString(dataBuffer.asInputStream());
                  log.info(bodyStr);
                } catch (IOException e) {
                  e.printStackTrace();
                }
                ServerHttpRequest mutatedRequest =
                    new ServerHttpRequestDecorator(exchange.getRequest()) {
                      @Override
                      public Flux<DataBuffer> getBody() {
                        dataBuffer.readPosition(0);
                        return Flux.just(dataBuffer);
                      }
                    };

                return chain.filter(exchange.mutate().request(mutatedRequest).build());
              });
    } else {
      return chain.filter(exchange);
    }
  }

  @Override
  public int getOrder() {
    return 100;
  }
}
