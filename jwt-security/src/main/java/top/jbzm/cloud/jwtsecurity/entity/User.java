package top.jbzm.cloud.jwtsecurity.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import top.jbzm.cloud.jwtsecurity.entity.base.Base;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jbzm
 * @date 2018下午5:40
 **/
@Entity
@Table(name = "users")
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class User extends Base {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}