package top.zhengyiwoaini.cloud.client.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jbzm
 * @date 2019-09-03 18:23
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class ClientDemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(ClientDemoApplication.class, args);
  }
}
