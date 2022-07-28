package com.social.moinda.core.domains.meeting.entity;

import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.meeting.dto.MeetingCreateResponse;
import com.social.moinda.core.domains.meeting.dto.MeetingDetails;
import com.social.moinda.core.domains.meetingmember.entity.MeetingMember;
import com.social.moinda.core.domains.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "meeting_id"))
@Table(name = "TABLE_MEETING")
@Entity
public class Meeting extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "group_id")
    private Group group;

    @Embedded
    private MeetingLocation meetingLocation;

    @Column(name = "amount", nullable = false)
    private int amount;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<MeetingMember> meetingMember = new ArrayList<>();

    /*
        TODO : 요청시 어떤 형식으로 받아올지, DB에 저장하는 방식(Converter사용)
     */
    @Column(name = "meeting_date", nullable = false)
    private LocalDateTime meetingDate;

    public Meeting(Group group,
                   MeetingLocation meetingLocation,
                   int amount,
                   LocalDateTime meetingDate) {
        this.group = group;
        this.meetingLocation = meetingLocation;
        this.amount = amount;
        this.meetingDate = meetingDate;
    }

    public MeetingCreateResponse bindToCreateResponse() {
        return new MeetingCreateResponse(
                this.getId(),
                this.meetingLocation,
                this.amount,
                this.meetingDate
        );
    }

    public MeetingDetails bindToMeetingDetails() {
        return new MeetingDetails(
                mappingToMemberDtoList(),
                this.meetingMember.size(),
                this.meetingDate
        );
    }

    private List<MemberDto> mappingToMemberDtoList() {
        return this.meetingMember.stream()
                .map(MeetingMember::convertToMemberDto)
                .collect(Collectors.toList());
    }
}
