package com.talbrain.vegas.gatewayservicenew.module.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jbzm
 * @date 2019-06-12 16:54
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gateway.filter")
public class FilterIgnoreProperties {
  private List<String> ignoreUri = new ArrayList<>();
}
