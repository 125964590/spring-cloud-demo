package top.jbzm.cloud.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author jbzm
 * @date Create on 2018/4/19 17:43
 */
@RestController
public class DcController {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    ConsumerService consumer;

    @GetMapping("consumer")
    public String dc() throws InterruptedException {
        return consumer.consumer();
    }

    @GetMapping("discovery")
    public Object look() {
        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-client");
        instances.forEach(x -> {
            System.out.println(x.getHost());
            System.out.println(x.getUri());
            System.out.println(x.getPort());
        });
        return discoveryClient.getInstances("eureka-client");
    }

    @Service
    class ConsumerService {
        @Autowired
        @LoadBalanced
        private RestTemplate restTemplate;

        @HystrixCommand(fallbackMethod = "fallback")
        public String consumer() {
            return restTemplate.getForObject("http://eureka-client/dc", String.class);
        }

        public String fallback() {
            return "fallback";
        }
    }
}