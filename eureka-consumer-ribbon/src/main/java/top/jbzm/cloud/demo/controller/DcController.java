package top.jbzm.cloud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author jbzm
 * @Date Create on 2018/4/19 17:43
 */
@RestController
public class DcController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("consumer")
    public String dc() {
        return restTemplate.getForObject("http://eureka-client/dc", String.class);
    }
}