package top.jbzm.cloud.jwtsecurity.entity.base;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author jbzm
 * @date 2018下午5:41
 **/
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"createDate", "updateDate"})
@MappedSuperclass
public abstract class Base implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @CreatedDate
    protected Instant createDate;

    @LastModifiedDate
    protected Instant updateDate;
}