package top.jbzm.test;

import com.ecwid.consul.v1.ConsistencyMode;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;

import java.util.List;
import java.util.Map;

/**
 * @author zhengyi
 * @date 2018-12-11 14:36
 **/
public class DeregisterServer {

    public static void main(String[] args) {
        ConsulClient client = new ConsulClient("192.168.100.47");
        Response<Map<String, List<String>>> catalogServices = client.getCatalogServices(new QueryParams(ConsistencyMode.STALE));
        catalogServices.getValue().forEach((k, v) -> {

        });
    }
}