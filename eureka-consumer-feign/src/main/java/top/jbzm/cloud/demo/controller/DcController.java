package top.jbzm.cloud.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.jbzm.cloud.demo.service.TestService;

/**
 * @author jbzm
 * @date Create on 2018/4/19 18:30
 */
@Slf4j
@RestController
public class DcController {
  private final TestService test;

  public DcController(TestService test) {
    this.test = test;
  }

  @GetMapping("test01")
  public Object test01(@RequestParam("lol") String lol) {
    Object o1 = new Object();
    o1 = lol;
    String s = (String) test.bodyTest();
    String s1 = (String) test.bodyTest();
    log.info(lol + s);
    log.info(lol + s);
    return lol;
  }
}
