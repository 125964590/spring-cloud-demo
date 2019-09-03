package com.talbrain.vegas.gatewayservicenew.feign;

import com.talbrain.vegas.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * resolve cross--domain issues
 *
 * @author jbzm
 * @date 2019-06-12 00:53
 */
@FeignClient(value = "auth-service")
public interface UserDetailService {

  /**
   * verify request path
   *
   * @param authorization auth parameter
   * @param requestUri request uri
   * @param requestCan specific parameter
   * @return verify status code
   */
  @GetMapping("/privilege/verify")
  Result privilegeVerify(
          @RequestParam(value = "authorization", required = false) String authorization,
          @RequestParam(value = "request_uri", required = false) String requestUri,
          @RequestParam(value = "request_can", required = false) String requestCan);
}
