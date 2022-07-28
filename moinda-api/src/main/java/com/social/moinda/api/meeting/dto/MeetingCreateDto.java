package com.social.moinda.api.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.meeting.entity.MeetingLocation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class MeetingCreateDto {

    // TODO : to Add Validation Annotation
    private Long groupId;
    private String shopName;
    private String streetName;

    private int amount;

    // TODO : request 시 어떤 형태의 값으로 보내줘야 하는가.
    // TODO : Custom Deserializer , https://jojoldu.tistory.com/361
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime meetingDate;

    public MeetingCreateDto(Long groupId,
                            String shopName,
                            String streetName,
                            int amount,
                            LocalDateTime meetingDate) {
        this.groupId = groupId;
        this.shopName = shopName;
        this.streetName = streetName;
        this.amount = amount;
        this.meetingDate = meetingDate;
    }

    public Meeting bindToEntity(Group group) {
        return new Meeting(
                group,
                new MeetingLocation(this.shopName, this.streetName),
                this.amount,
                this.meetingDate
        );
    }
}
