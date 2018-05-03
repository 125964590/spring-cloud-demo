package top.jbzm.cloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author jbzm
 * @date Create on 2018/4/19 17:43
 * 这里使用SpringCloudApplication注解该注解包含了其他的三个注解由此可以见
 * SpringCloud自动的支持了负载均衡/注册/熔断器/springboot
 */
@SpringCloudApplication
@EnableHystrixDashboard
public class EurekaConsumerReibbonHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerReibbonHystrixApplication.class, args);
    }
}
