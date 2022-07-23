package com.social.moinda.api.meeting.service;


import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.meeting.entity.MeetingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MeetingCommandServiceTests {

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private MeetingCommandService meetingCommandService;

    @DisplayName("모임 생성에 성공한다. - 성공")
    @Test
    void createMeetingSuccessTest() {
        Long groupId = 1L;
        MeetingCreateDto meetingCreateDto = new MeetingCreateDto(groupId,
                "스타벅스 센트럴",
                "경기도 부천시 소향로 181",
                4500,
                LocalDateTime.of(2022, 7, 31, 2, 30));

        // TODO : 해당 테스트에 맞지 않은 성격의 모델코드
        GroupCreateDto groupCreateDto = new GroupCreateDto(
                1L,
                "모임입니다!!",
                "소개글입니다.",
                "경기도",
                "부천시",
                "FREE",
                300
        );

        Group group = groupCreateDto.bindToEntity();

        Meeting meeting = meetingCreateDto.bindToEntity(group);

        given(groupRepository.findById(anyLong())).willReturn(Optional.of(group));
        given(meetingRepository.save(any(Meeting.class))).willReturn(meeting);

        meetingCommandService.create(meetingCreateDto);

        then(groupRepository).should(times(1)).findById(anyLong());
        then(meetingRepository).should(times(1)).save(any(Meeting.class));
    }
}
