package com.social.moinda.core.domains.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.social.moinda.core.domains.meeting.entity.MeetingLocation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingCreateResponse {

    private Long meetingId;
    private MeetingLocation meetingLocation;
    private int amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime meetingDate;

    public MeetingCreateResponse(Long meetingId,
                                 MeetingLocation meetingLocation,
                                 int amount,
                                 LocalDateTime meetingDate) {
        this.meetingId = meetingId;
        this.meetingLocation = meetingLocation;
        this.amount = amount;
        this.meetingDate = meetingDate;
    }
}

