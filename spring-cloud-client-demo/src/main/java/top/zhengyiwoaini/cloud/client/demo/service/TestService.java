package top.zhengyiwoaini.cloud.client.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author jbzm
 * @date 2019-11-01 16:25
 */
@FeignClient(value = "aiclient-test")
public interface TestService {
  @PostMapping("bodyTest")
  String bodyTest();

  @GetMapping("error01")
  String error01();
}
