package top.jbzm.cloud.demo.service.impl;

import org.springframework.stereotype.Component;
import top.jbzm.cloud.demo.service.DcClient;

/**
 * @author jbzm
 * @date 2018下午2:56
 **/
@Component
public class DcClientFallback implements DcClient {
    @Override
    public String consumer() {
        return "fallback-dc";
    }
}
