package top.jbzm.cloud.demo.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author jbzm
 * @Date Create on 2018/4/19 18:27
 */
@FeignClient("eureka-client")
public interface DcClient {
    /**
     * 调用eureka-client服务的接口
     * @return
     */
    @GetMapping("/dc")
    String consumer();
}
