//package com.talbrain.vegas.gatewayservicenew.listener;
//
//import com.ctrip.framework.apollo.enums.PropertyChangeType;
//import com.ctrip.framework.apollo.model.ConfigChange;
//import com.ctrip.framework.apollo.model.ConfigChangeEvent;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
//import com.netflix.discovery.converters.Auto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.scope.refresh.RefreshScope;
//import org.springframework.cloud.endpoint.event.RefreshEvent;
//import org.springframework.cloud.gateway.config.GatewayProperties;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
//import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author jbzm
// * @date 2019-06-18 20:59 @Slf4j
// */
//@Slf4j
//@Component
//public class ApolloPropertiesRefresher implements ApplicationContextAware, EnvironmentAware {
//  private ApplicationContext applicationContext;
//
//  private RouteDefinitionWriter routeDefinitionWriter;
//  private Environment environment;
//  private RouteDefinitionRepository routeDefinitionRepository;
//
//  private final RefreshScope refreshScope;
//
//  public ApolloPropertiesRefresher(RefreshScope refreshScope) {
//    this.refreshScope = refreshScope;
//  }
//
//  @ApolloConfigChangeListener("dynamic-gateway-new")
//  public void onChange(ConfigChangeEvent configChangeEvent) {
//    Map<PropertyChangeType, List<ConfigChange>> groupByChangeType =
//        configChangeEvent.changedKeys().stream()
//            .map(configChangeEvent::getChange)
//            .collect(Collectors.groupingBy(ConfigChange::getChangeType));
//    RouteDefinition routeDefinition = new RouteDefinition();
//    routeDefinitionRepository.save(Mono.just(routeDefinition));
//    new RefreshEvent(this,"lol","lol");
//
//    executeChange(groupByChangeType);
//    // sent refresh gateway routes event
//    publish(configChangeEvent);
//  }
//
//  private void executeChange(Map<PropertyChangeType, List<ConfigChange>> groupByChangeType) {
//
//  }
//
//  private void publish(ConfigChangeEvent configChangeEvent) {
//    applicationContext.publishEvent(new RefreshEvent(this, "refresh", "refresh"));
//  }
//
//  @Override
//  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//    this.applicationContext = applicationContext;
//  }
//
//  @Override
//  public void setEnvironment(Environment environment) {
//    this.environment = environment;
//  }
//}
