package com.social.moinda.core;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {

    // TODO : 중간테이블에서 ID만 사용하므로 분리 필요
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

}
