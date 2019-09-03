package com.talbrain.vegas.gatewayservicenew.tool.handler;

import com.talbrain.vegas.domain.Result;
import com.talbrain.vegas.domain.SystemResponseStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author jbzm
 * @date 2019-06-25 00:28
 */
@Component
public class FallBackHandler implements HandlerFunction<ServerResponse> {
  @Override
  public Mono<ServerResponse> handle(ServerRequest request) {
    return ServerResponse.ok()
        .body(Mono.just(new Result(SystemResponseStatus.SUCCESS, "请求过于频繁请稍后再试")), Result.class);
  }
}
