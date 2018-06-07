package top.jbzm.cloud.jwtsecurity.entity.base;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author jbzm
 * @date 2018下午5:41
 **/
@Getter
@Setter
@MappedSuperclass
public class Base implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    protected Integer createDate;

    @LastModifiedDate
    @Column(nullable = false)
    protected Integer updateDate;
}