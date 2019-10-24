package top.jbzm.cloud.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jbzm.cloud.hystrix.service.ConsumerService;

import java.util.List;

/**
 * @author jbzm
 * @date Create on 2018/4/19 17:43
 */
@RestController
public class DcController {
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
        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-client-a");
        instances.forEach(x -> {
            System.out.println(x.getHost());
            System.out.println(x.getUri());
            System.out.println(x.getPort());
        });
        return discoveryClient.getInstances("eureka-client-a");
    }

}