package top.jbzm.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** @Author jbzm @Date Create on 2018/4/19 17:17 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @GetMapping("test03")
  public String lol(String lol) {
    return lol;
  }
}
