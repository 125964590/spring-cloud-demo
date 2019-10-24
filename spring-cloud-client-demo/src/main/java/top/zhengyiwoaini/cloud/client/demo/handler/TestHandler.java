package top.zhengyiwoaini.cloud.client.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author jbzm
 * @date 2019-09-03 18:40
 */
@Slf4j
@Component
public class TestHandler {
  public Mono<ServerResponse> getName(ServerRequest request) {
    Mono<String> name = Mono.just(request.pathVariable("name"));
    return ok().body(name, String.class);
  }

  public Mono<ServerResponse> getDecorator(ServerRequest serverRequest) {
    String s = resolveBodyFromRequest(serverRequest);
    log.info(s);
    return ServerResponse.ok()
        .contentType(MediaType.TEXT_PLAIN)
        .body(BodyInserters.fromObject("Hello, decorator!" + s));
  }

  public Mono<ServerResponse> getDefault(ServerRequest serverRequest) {
    final AtomicReference<String>[] lol = new AtomicReference[] {null};
    serverRequest
        .bodyToMono(String.class)
        .subscribe(
            x -> {
              lol[0].set(x);
            });
    return ServerResponse.ok()
        .contentType(MediaType.TEXT_PLAIN)
        .body(BodyInserters.fromObject("Hello, default!" + lol.toString()));
  }

  private String resolveBodyFromRequest(ServerRequest serverHttpRequest) {
    // 获取请求体
    Mono<DataBuffer> dataBufferMono = serverHttpRequest.bodyToMono(DataBuffer.class);
    StringBuilder sb = new StringBuilder();
    dataBufferMono.subscribe(
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
