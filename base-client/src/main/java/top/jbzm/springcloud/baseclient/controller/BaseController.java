package top.jbzm.springcloud.baseclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * @author jbzm
 * @date 4/15/20 9:00 PM
 */
@Slf4j
@RequestMapping("/base")
@RestController
public class BaseController {

  @GetMapping
  public Mono test() {

    log.info(String.valueOf(System.currentTimeMillis()));
    return Mono.create(
        x -> {
          HashMap<Object, Object> map = new HashMap<>();
          map.put("msg", "success");
          map.put("code", 2000000);
          x.success(map);
        });
  }
}
