package top.zhengyiwoaini.cloud.client.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jbzm
 * @date 2019-10-21 15:17
 */
@Slf4j
@RestController
public class TestController {

  @PostMapping("bodyTest")
  public Object bodyTest(@RequestBody Object object) {
    log.info(object.toString());
    return object;
  }
}
