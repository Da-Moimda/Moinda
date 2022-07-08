package com.social.talk.core.domains.member.entity;


import com.social.talk.core.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
public class Member extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

}
