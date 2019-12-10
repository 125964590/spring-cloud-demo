package com.talbrain.vegas.gatewayservicenew.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * print request time
 *
 * @author jbzm
 * @date 2019-10-21 14:50
 */
@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

  private static final String START_TIME = "startTime";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
    return chain
        .filter(exchange)
        .then(
            Mono.fromRunnable(
                () -> {
                  Long startTime = exchange.getAttribute(START_TIME);
                  if (startTime != null) {
                    StringBuffer stringBuffer = new StringBuffer();
                    MultiValueMap<String, String> queryParams =
                        exchange.getRequest().getQueryParams();
                    queryParams.forEach(
                        (k, v) -> {
                          stringBuffer.append(k).append(":");
                          stringBuffer.append(v).append("   ");
                        });
                    long executeTime = (System.currentTimeMillis() - startTime);
                    log.info(
                        exchange.getRequest().getURI().getRawPath()
                            + " : "
                            + executeTime
                            + "ms"
                            + " request parameter:"
                            + stringBuffer.toString());
                  }
                }));
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }
}
