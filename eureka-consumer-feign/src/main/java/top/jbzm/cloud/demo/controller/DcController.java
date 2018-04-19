package top.jbzm.cloud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jbzm.cloud.demo.service.DcClient;

/**
 * @author jbzm
 * @date Create on 2018/4/19 18:30
 */
@RestController
public class DcController {
    @Autowired
    private DcClient dcClient;

    @GetMapping("consumer")
    public String consumer() {
        return dcClient.consumer();
    }
}
