package top.zhengyiwoaini.cloud.client.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import top.zhengyiwoaini.cloud.client.demo.service.TestService;

import java.security.InvalidParameterException;

/**
 * @author jbzm
 * @date 2019-10-21 15:17
 */
@Slf4j
@RestController
public class TestController implements TestService {

  @Override
  public String bodyTest() {
    log.info("lol");
    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "lol";
  }

  @Override
  public String error01() {
    throw new InvalidParameterException("error01");
  }
}
