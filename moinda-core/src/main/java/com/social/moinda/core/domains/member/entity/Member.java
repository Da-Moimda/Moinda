package com.social.moinda.core.domains.member.entity;


import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.group.dto.Groups;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.groupmember.entity.GroupMember;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.core.domains.member.dto.SignupResponse;
import lombok.*;

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
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
@Table(name = "TABLE_MEMBER")
@Entity
@ToString(exclude = {"groupMember"})
public class Member extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<GroupMember> groupMember = new ArrayList<>();

    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public MemberDto bindToMemberDto() {
        return new MemberDto(
                this.getId(),
                this.email,
                this.name
        );
    }

    public SignupResponse bindToSignupResponse() {
        return new SignupResponse(
                this.getId(),
                this.email,
                this.name,
                this.password
        );
    }

    /**
     *  TODO : mappingToMemberDtoList처럼 변경 필요
     *
     */
    public List<GroupDto> bindToGroupDtoList() {
        List<Group> groups = this.groupMember.stream()
                .map(GroupMember::getGroup)
                .collect(Collectors.toList());

        return new Groups(groups).getGroupDtoList();
    }
}
