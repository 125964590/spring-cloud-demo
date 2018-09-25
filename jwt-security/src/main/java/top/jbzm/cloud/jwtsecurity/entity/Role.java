package top.jbzm.cloud.jwtsecurity.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import top.jbzm.cloud.jwtsecurity.entity.base.Base;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author jbzm
 * @date 2018下午5:52
 **/
@Table(name = "role")
@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class Role extends Base {

    @Size(max = 20)
    private String roleName;
}