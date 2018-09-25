package top.jbzm.cloud.jwtsecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import top.jbzm.cloud.jwtsecurity.entity.Role;
import top.jbzm.cloud.jwtsecurity.entity.User;
import top.jbzm.cloud.jwtsecurity.repository.RoleRepository;
import top.jbzm.cloud.jwtsecurity.repository.UserRepository;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtSecurityApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void contextLoads() {
        Set<Role> roles=new HashSet<>();
        Role role = roleRepository.findByRoleName("admin");
        roles.add(role);
        User user=User.builder().username("jbzm1").password(passwordEncoder.encode("jbzm1")).roles(roles).build();
        userRepository.save(user);
    }

}
