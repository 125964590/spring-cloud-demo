package top.jbzm.cloud.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import top.jbzm.cloud.demo.service.impl.DcClientFallback;

/**
 * @Author jbzm
 * @Date Create on 2018/4/19 18:27
 */
@FeignClient(name = "eureka-client-a",fallback = DcClientFallback.class)
public interface DcClient {
    /**
     * 调用eureka-client服务的接口
     * @return
     */
    @GetMapping("/dc")
    String consumer();
}
