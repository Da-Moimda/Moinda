package com.social.moinda.core.domains.groupmember.entity;


import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "group_member_id"))
@Table(name = "TABLE_GROUPMEMBER")
@Entity
public class GroupMember extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "member_id")
    private Member member;

    public GroupMember(Group group, Member member) {
        this.group = group;
        this.member = member;
    }
}
