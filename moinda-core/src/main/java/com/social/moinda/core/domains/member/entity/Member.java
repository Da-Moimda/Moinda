package com.social.moinda.core.domains.member.entity;


import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
    ArgumentResolver + @AuthenticationPrincipal , Filter 를 사용해서
    어떻게 받아올지 설정해야 한다.
 */
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

    public String getEmail() {
        return email;
    }

    public MemberDto bindToMemberDto() {
        return new MemberDto(
                this.getId(),
                this.email,
                this.name,
                this.password
        );
    }
}
