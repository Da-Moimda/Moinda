package com.social.moinda.core.domains.meeting;

import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.group.entity.Group;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "meeting_id"))
@Entity
@ToString
public class Meeting extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "group_id")
    private Group group;

    @Embedded
    private MeetingLocation meetingLocation;

    @Column(name = "amount", nullable = false)
    private int amount;

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
}
