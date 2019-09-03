package top.zhengyiwoaini.actuator;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author jbzm
 * @date 2019-08-29 18:19
 */
@EnableAdminServer
@EnableEurekaClient
@SpringBootApplication
public class ActuatorApplication {
  public static void main(String[] args) {
    SpringApplication.run(ActuatorApplication.class, args);
  }
}
