package top.jbzm.cloud.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.jbzm.cloud.jwtsecurity.entity.User;

import java.util.Optional;

/**
 * @author jbzm
 * @date 2018下午6:10
 **/
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * find by username
     * @param username username
     * @return user
     */
    Optional<User> findByUsername(String username);
}