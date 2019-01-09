package top.jbzm.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhengyi
 * @date 2018-12-11 17:48
 **/
public class IpTest {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress byName = InetAddress.getByName(null);
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }
}