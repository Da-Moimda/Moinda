package com.social.moinda.core.domains.meeting.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class MeetingDto {

    private Long meetingId;
    private String meetingPlace;

    private int amount;

    private LocalDateTime meetingDate;
    public MeetingDto(Long meetingId,
                      String meetingPlace,
                      int amount,
                      LocalDateTime meetingDate) {
        this.meetingId = meetingId;
        this.meetingPlace = meetingPlace;
        this.amount = amount;
        this.meetingDate = meetingDate;
    }

}
