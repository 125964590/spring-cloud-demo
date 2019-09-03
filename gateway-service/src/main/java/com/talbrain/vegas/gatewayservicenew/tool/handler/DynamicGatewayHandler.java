package com.talbrain.vegas.gatewayservicenew.tool.handler;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;

/**
 * @author jbzm
 * @date 2019-06-25 23:10
 */
@Component
@AllArgsConstructor
public class DynamicGatewayHandler {
  private final RouteDefinitionWriter routeDefinitionWriter;


}
