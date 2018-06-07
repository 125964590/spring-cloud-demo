package top.jbzm.cloud.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.jbzm.cloud.jwtsecurity.entity.Role;
import top.jbzm.cloud.jwtsecurity.entity.User;

/**
 * @author jbzm
 * @date 2018下午6:10
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {

}