package top.jbzm.demo.springadmindemo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jbzm
 * @date 2019-06-11 18:19
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class SpringAdminDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringAdminDemoApplication.class, args);
  }
}
