package top.jbzm.demo.gatewaydemoservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jbzm
 * @date 2019-06-10 17:52
 */
@RestController
public class TestController {
  ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

  @GetMapping("/retry")
  public String testRetryByException(
      @RequestParam("key") String key, @RequestParam(name = "count") int count) {
    AtomicInteger num = map.computeIfAbsent(key, s -> new AtomicInteger());
    // 对请求或重试次数计数
    int i = num.incrementAndGet();
    // 计数i小于重试次数2抛出异常，让Spring Cloud Gateway进行重试
    if (i < count) {
      throw new RuntimeException("Deal with failure, please try again!");
    }
    // 当重试两次时候，清空计数，返回重试两次成功
    map.clear();
    System.out.println("lol");
    return "重试" + count + "次成功！";
  }
}
