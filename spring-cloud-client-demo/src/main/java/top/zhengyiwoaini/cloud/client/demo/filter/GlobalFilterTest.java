package top.zhengyiwoaini.cloud.client.demo.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author jbzm
 * @date 2019-12-31 16:18
 */
@Component
public class GlobalFilterTest implements GlobalFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    HttpHeaders headers = exchange.getRequest().getHeaders();
    System.out.println(headers.toString());
    return chain.filter(exchange);
  }
}
