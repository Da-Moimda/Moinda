package com.social.moinda.core.domains.meetingmember.entity;


import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.core.domains.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "meeting_member_id"))
@Table(name = "TABLE_MEETINGMEMBER")
@Entity
public class MeetingMember extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "member_id")
    private Member member;

    public MeetingMember(Meeting meeting, Member member) {
        this.meeting = meeting;
        this.member = member;
    }

    public MemberDto convertToMemberDto() {
        return this.member.bindToMemberDto();
    }
}