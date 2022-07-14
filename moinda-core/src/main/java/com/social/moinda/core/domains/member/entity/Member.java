package com.social.moinda.core.domains.member.entity;


import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.member.dto.MemberDto;
import lombok.*;

import javax.persistence.*;

/*
    ArgumentResolver + @AuthenticationPrincipal , Filter 를 사용해서
    어떻게 받아올지 설정해야 한다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
@Entity
public class Member extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "group_id")
    private Group group;

    public void setGroup(Group group) {
        this.group = group;
    }

    // TODO : CreateResponse 클래스로 변경해서 사용하기
    public MemberDto bindToMemberDto() {
        return new MemberDto(
                this.getId(),
                this.email,
                this.name,
                this.password
        );
    }
}
