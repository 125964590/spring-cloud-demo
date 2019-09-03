package com.talbrain.vegas.gatewayservicenew.filter;

import com.talbrain.vegas.domain.Result;
import com.talbrain.vegas.domain.exception.BizException;
import com.talbrain.vegas.domain.exception.ErrorResult;
import com.talbrain.vegas.domain.exception.judge.MyAssert;
import com.talbrain.vegas.gatewayservicenew.feign.UserDetailService;
import com.talbrain.vegas.gatewayservicenew.module.properties.FilterIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.print.URIException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;

import static com.talbrain.vegas.gatewayservicenew.module.constant.SecurityConstant.AUTH_TOKEN;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;

/**
 * permission validation global filter
 *
 * @author jbzm
 * @date 2019-06-12 17:52
 */
@Component
@AllArgsConstructor
public class GlobalAuthFilter implements GlobalFilter, Ordered {

  public static final ErrorResult TOKEN_IS_LOSE = ErrorResult.create(4000001, "请检查是否携带token");

  private final FilterIgnoreProperties filterIgnoreProperties;
  private final UserDetailService userDetailService;

  @Override
  @SneakyThrows
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    // get origin path -> usr custom StripPrefixFilter
    LinkedHashSet<URI> uris = exchange.getRequiredAttribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
    String originPath =
        uris.stream()
            .findAny()
            .orElseThrow(() -> new URISyntaxException("the uri cannot be empty", " get uri fail"))
            .getPath();
    // check path is unfiltered
    boolean matchResult = isMatchResult(originPath);
    if (matchResult) {
      return chain.filter(exchange);
    }
    String originalToken =
        Optional.ofNullable(request.getHeaders().getFirst(AUTH_TOKEN))
            .orElseThrow(() -> new BizException(TOKEN_IS_LOSE));
    // request path authorization
    Result result =
        userDetailService.privilegeVerify(
            originalToken,
            originPath,
            // get request param
            Optional.ofNullable(request.getQueryParams().get("request_can"))
                .flatMap((x) -> Optional.ofNullable(x.get(0)))
                .orElse(""));
    // processing return result
    MyAssert.checkResponse(result);
    return chain.filter(exchange);
  }

  private boolean isMatchResult(String originPath) {
    return filterIgnoreProperties.getIgnoreUri().stream()
        .anyMatch(
            uri -> {
              AntPathMatcher antPathMatcher = new AntPathMatcher();
              return antPathMatcher.match(uri, originPath);
            });
  }

  @Override
  public int getOrder() {
    return 1000;
  }
}
