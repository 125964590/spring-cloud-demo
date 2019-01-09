package top.jbzm.test;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.health.model.HealthService;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhengyi
 * @date 2018-12-10 10:44
 **/
public class ConsulApiTest {
    public static void main(String[] args) {
        ConsulClient client = new ConsulClient("192.168.100.46");
//
//// set KV
//        byte[] binaryData = new byte[]{1, 2, 3, 4, 5, 6, 7};
//        client.setKVBinaryValue("someKey", binaryData);
//
//        client.setKVValue("com.my.app.foo", "foo");
//        client.setKVValue("com.my.app.bar", "bar");
//        client.setKVValue("com.your.app.foo", "hello");
//        client.setKVValue("com.your.app.bar", "world");

// get single KV for key
//        Response<GetValue> keyValueResponse = client.getKVValue("com.my.app.foo");
//        System.out.println(keyValueResponse.getValue().getKey() + ": " + keyValueResponse.getValue().getDecodedValue()); // prints "com.my.app.foo: foo"
//
//// get list of KVs for key prefix (recursive)
//        Response<List<GetValue>> keyValuesResponse = client.getKVValues("com.my");
//        keyValuesResponse.getValue().forEach(value -> System.out.println(value.getKey() + ": " + value.getDecodedValue())); // prints "com.my.app.foo: foo" and "com.my.app.bar: bar"
//
////list known datacenters
//        Response<List<String>> response = client.getCatalogDatacenters();
//        System.out.println("Datacenters: " + response.getValue());

// register new service
        NewService newService = new NewService();
        newService.setId("myapp_01");
        newService.setName("myapp");
        newService.setTags(Arrays.asList("EU-West", "EU-East"));
        newService.setPort(8080);
        client.agentServiceRegister(newService);

// register new service with associated health check
//        NewService newService = new NewService();
//        newService.setId("myapp_02");
//        newService.setTags(Collections.singletonList("EU-East"));
//        newService.setName("myapp");
//        newService.setPort(8080);

//        NewService.Check serviceCheck = new NewService.Check();
//        serviceCheck.setScript("/usr/bin/some-check-script");
//        serviceCheck.setInterval("10s");
//        newService.setCheck(serviceCheck);
//
//        client.agentServiceRegister(newService);

// query for healthy services based on name (returns myapp_01 and myapp_02 if healthy)
        Response<List<HealthService>> healthyServices = client.getHealthServices("myapp", true, QueryParams.DEFAULT);
        healthyServices.getValue().forEach(x -> System.out.println(x.getService().getService()));

// query for healthy services based on name and tag (returns myapp_01 if healthy)
//        Response<List<HealthService>> healthyServices = client.getHealthServices("myapp", "EU-West", true, QueryParams.DEFAULT);
    }
}