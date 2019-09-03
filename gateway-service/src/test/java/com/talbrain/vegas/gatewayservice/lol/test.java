package com.talbrain.vegas.gatewayservice.lol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author jbzm
 * @date 2019-07-01 18:56
 */
@Configuration
public class test {
  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public A craeteA(B b) {
    return new A(b);
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public B createB(C c) {
    return new B(c);
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public C createC(A a) {
    System.out.println("lol");
    return new C(a);
  }
}
