package com.social.moinda.web.meeting;

import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.api.meeting.service.MeetingCommandService;
import com.social.moinda.core.domains.meeting.dto.MeetingCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting")
public class MeetingApiController {

    private final MeetingCommandService meetingCommandService;

    @PostMapping("")
    public ResponseEntity<MeetingCreateResponse> create(@RequestBody MeetingCreateDto dto) {

        MeetingCreateResponse meetingCreateResponse = meetingCommandService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(meetingCreateResponse);
    }

}
