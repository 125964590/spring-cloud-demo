package top.jbzm.cloud.demo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * @author jbzm
 * @date Create on 2018/4/20 13:30
 */
@Configuration
public class MyConfig {
    @Bean
    @LoadBalanced
    public RestTemplate loadTemplate() {
        return new RestTemplate();
    }

    @Primary
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
