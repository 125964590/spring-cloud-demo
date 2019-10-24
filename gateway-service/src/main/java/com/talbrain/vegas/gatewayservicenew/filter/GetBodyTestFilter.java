package com.talbrain.vegas.gatewayservicenew.filter;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author jbzm
 * @date 2019-09-04 10:31
 */
@Slf4j
// @Component
public class GetBodyTestFilter implements GlobalFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    RequestPath path = request.getPath();
    if (path.toString().contains("default")) {
      AtomicReference<DataBuffer> dataBuffer = null;
      DefaultServerRequest defaultServerRequest = new DefaultServerRequest(exchange);
      defaultServerRequest
          .bodyToFlux(String.class)
          .subscribe(
              body -> {
                JSONObject jsonObject = JSONObject.parseObject(body);
                jsonObject.put("lol", "jbzm");
                dataBuffer.set(stringBuffer(jsonObject.toJSONString()));
              });
      Flux<DataBuffer> bodyFlux = Flux.just(dataBuffer.get());
      request =
          new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
              return bodyFlux;
            }
          };
      return chain.filter(exchange.mutate().request(request).build());
    } else if (path.toString().contains("decorator")) {
      String body = getBody(request);
      JSONObject jsonObject = JSONObject.parseObject(body);
      jsonObject.put("lol", "jbzm");
      DataBuffer dataBuffer = stringBuffer(jsonObject.toJSONString());
      Flux<DataBuffer> bodyFlux = Flux.just(dataBuffer);
      request =
          new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
              return bodyFlux;
            }
          };
      return chain.filter(exchange.mutate().request(request).build());
    }

    return chain.filter(exchange.mutate().request(request).build());
  }

  private String getBody(ServerHttpRequest request) {
    Flux<DataBuffer> body = request.getBody();
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

  private DataBuffer stringBuffer(String value) {
    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

    NettyDataBufferFactory nettyDataBufferFactory =
        new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
    DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
    buffer.write(bytes);
    return buffer;
  }
}
