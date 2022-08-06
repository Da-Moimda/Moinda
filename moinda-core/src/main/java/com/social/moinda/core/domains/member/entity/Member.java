package com.social.moinda.core.domains.member.entity;


import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.groupmember.entity.GroupMember;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.core.domains.member.dto.SignupResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    ArgumentResolver + @AuthenticationPrincipal , Filter 를 사용해서
    어떻게 받아올지 설정해야 한다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
@Table(name = "TABLE_MEMBER")
@Entity
public class Member extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "introduce", length = 50)
    private String introduce;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<GroupMember> groupMember = new ArrayList<>();

    public Member(String email, String name, String introduce, String password) {
        this.email = email;
        this.name = name;
        this.introduce = introduce;
        this.password = password;
    }

    public MemberDto bindToMemberDto() {
        return new MemberDto(
                this.getId(),
                this.email,
                this.name,
                this.introduce
        );
    }

    public SignupResponse bindToSignupResponse() {
        return new SignupResponse(
                this.getId(),
                this.email,
                this.name,
                this.introduce,
                this.password
        );
    }

    public List<GroupDto> bindToGroupDtoList() {
        return this.groupMember.stream()
                .map(GroupMember::convertToGroupDto)
                .collect(Collectors.toList());
    }
}
