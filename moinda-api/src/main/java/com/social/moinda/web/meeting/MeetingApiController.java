package com.social.moinda.web.meeting;

import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.api.meeting.dto.MeetingJoinRequest;
import com.social.moinda.api.meeting.service.MeetingCommandService;
import com.social.moinda.api.meeting.service.MeetingQueryService;
import com.social.moinda.core.domains.meeting.dto.MeetingCreateResponse;
import com.social.moinda.core.domains.meeting.dto.MeetingDetails;
import com.social.moinda.core.domains.meeting.dto.MeetingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting")
public class MeetingApiController {

    private final MeetingCommandService meetingCommandService;

    private final MeetingQueryService meetingQueryService;

    @PostMapping("")
    public ResponseEntity<MeetingCreateResponse> create(@RequestBody MeetingCreateDto dto) {
        MeetingCreateResponse meetingCreateResponse = meetingCommandService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(meetingCreateResponse);
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody @Validated MeetingJoinRequest meetingJoinRequest) {
        meetingCommandService.joinMeeting(meetingJoinRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<MeetingDto>> getMeetings(@PathVariable Long groupId) {
        List<MeetingDto> meetings = meetingQueryService.getMeetings(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(meetings);
    }

    @GetMapping("/{meetingId}/details")
    public ResponseEntity<MeetingDetails> getDetails(@PathVariable Long meetingId) {
        MeetingDetails meetingDetails = meetingQueryService.getMeetingDetails(meetingId);
        return ResponseEntity.status(HttpStatus.OK).body(meetingDetails);
    }

}
