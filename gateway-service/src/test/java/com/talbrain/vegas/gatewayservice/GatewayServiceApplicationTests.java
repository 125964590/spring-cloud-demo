package com.talbrain.vegas.gatewayservice;

import com.talbrain.vegas.gatewayservice.lol.A;
import com.talbrain.vegas.gatewayservice.lol.B;
import com.talbrain.vegas.gatewayservice.lol.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayServiceApplicationTests {

    @Autowired
    private A a;

    @Autowired private B b;

    @Autowired private C c;

    @Test
    public void test01() {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println("lol");
    }
}
