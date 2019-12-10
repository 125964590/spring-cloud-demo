package com.talbrain.vegas.gatewayservicenew.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                byte[] dataBytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(dataBytes);
                log.info(new String(dataBytes));
                DataBufferUtils.release(dataBuffer);
                ServerHttpRequest mutatedRequest =
                    new ServerHttpRequestDecorator(exchange.getRequest()) {
                      @Override
                      public Flux<DataBuffer> getBody() {
                        DataBuffer nettyDataBuffer = factory.allocateBuffer(dataBytes.length);
                        nettyDataBuffer.write(dataBytes);
                        return Flux.just(nettyDataBuffer);
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
