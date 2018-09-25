package top.jbzm.cloud.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.jbzm.cloud.jwtsecurity.entity.Role;
import top.jbzm.cloud.jwtsecurity.entity.User;

import javax.validation.constraints.Size;

/**
 * @author jbzm
 * @date 2018下午6:10
 **/
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(@Size(max = 20) String roleName);
}