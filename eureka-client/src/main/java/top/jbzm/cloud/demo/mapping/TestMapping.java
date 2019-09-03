package top.jbzm.cloud.demo.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.jbzm.cloud.demo.handler.TestHandler;

/**
 * @author jbzm
 * @date 2019-06-24 22:43
 */
@Configuration
public class TestMapping {

  private final TestHandler testHandler;

  public TestMapping(TestHandler testHandler) {
    this.testHandler = testHandler;
  }

  @Bean
  public RouterFunction<ServerResponse> serverResponseRouterFunction() {
    return RouterFunctions.route(RequestPredicates.GET("/test01"), testHandler)
        .and(
            RouterFunctions.route(
                RequestPredicates.GET("/test02"),
                request ->
                    ServerResponse.ok()
                        .body(Mono.just(request.queryParam("lol").toString()), String.class)));
  }
}
