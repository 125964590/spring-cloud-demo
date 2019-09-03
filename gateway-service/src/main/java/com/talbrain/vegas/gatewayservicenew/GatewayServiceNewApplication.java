package com.talbrain.vegas.gatewayservicenew;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jbzm
 * @date 2019-06-11 18:19
 */
@EnableApolloConfig
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class GatewayServiceNewApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayServiceNewApplication.class, args);
  }
}
