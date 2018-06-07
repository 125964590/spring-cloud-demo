package top.jbzm.cloud.jwtsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.jbzm.cloud.jwtsecurity.entity.User;
import top.jbzm.cloud.jwtsecurity.repository.UserRepository;

/**
 * @author jbzm
 * @date 2018下午7:01
 **/
@Service
public class CustomUserdDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserdDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username : " + username)
        );
        return UserInfo.create(user);
    }

    /**
     * @param id userId
     * @return UserInfo
     */
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return UserInfo.create(user);
    }
}