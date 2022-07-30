package com.social.moinda.api.meeting.service;


import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.group.exception.NotFoundGroupException;
import com.social.moinda.api.meeting.dto.MeetingCreateDto;
import com.social.moinda.api.meeting.dto.MeetingJoinRequest;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import com.social.moinda.core.domains.groupmember.entity.GroupMember;
import com.social.moinda.core.domains.groupmember.entity.GroupMemberQueryRepository;
import com.social.moinda.core.domains.meeting.entity.Meeting;
import com.social.moinda.core.domains.meeting.entity.MeetingQueryRepository;
import com.social.moinda.core.domains.meeting.entity.MeetingRepository;
import com.social.moinda.core.domains.meetingmember.entity.MeetingMember;
import com.social.moinda.core.domains.meetingmember.entity.MeetingMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Mock
    private MeetingMemberRepository meetingMemberRepository;

    @Mock
    private GroupMemberQueryRepository groupMemberQueryRepository;

    @Mock
    private MeetingQueryRepository meetingQueryRepository;

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

    @DisplayName("그룹을 찾을 수 없어서 모임 생성에 실패한다")
    @Test
    void createMeetingFailTest() {
        MeetingCreateDto meetingCreateDto = new MeetingCreateDto(1L,
                "스타벅스 센트럴",
                "경기도 부천시 소향로 181",
                4500,
                LocalDateTime.of(2022, 7, 31, 2, 30));

        given(groupRepository.findById(anyLong()))
                .willThrow(new NotFoundGroupException());

        assertThatThrownBy(() -> meetingCommandService.create(meetingCreateDto))
                .isInstanceOf(NotFoundGroupException.class)
                .hasMessageContaining("존재하지 않는 그룹입니다.");

        then(groupRepository).should(times(1)).findById(anyLong());
        then(groupRepository).shouldHaveNoMoreInteractions();
        then(meetingRepository).shouldHaveNoInteractions();
    }

    @DisplayName("모임에 참여하는 것을 성공한다.")
    @Test
    void joinInMeetingSuccessTest() {

        Long meetingId = 1L;
        Long memberId = 1L;
        Long groupId = 1L;
        // 사용자 ID, 그룹ID, 모임ID
        MeetingJoinRequest meetingJoinRequest = new MeetingJoinRequest(meetingId, memberId, groupId);

        GroupMember groupMember = new GroupMember(null, null);
        Meeting meeting = new Meeting(null, null, 5000, LocalDateTime.of(2022, 7, 31, 2, 30));

        MeetingMember meetingMember = new MeetingMember(null, null);

        given(groupMemberQueryRepository.findGroupMemberById(anyLong(), anyLong()))
                .willReturn(Optional.of(groupMember));
        given(meetingQueryRepository.findById(anyLong())).willReturn(Optional.of(meeting));
        given(meetingMemberRepository.save(any(MeetingMember.class)))
                .willReturn(meetingMember);

        meetingCommandService.joinMeeting(meetingJoinRequest);

        then(groupMemberQueryRepository).should(times(1)).findGroupMemberById(groupId, memberId);
        then(meetingQueryRepository).should(times(1)).findById(meetingId);
        then(meetingMemberRepository).should(times(1)).save(any(MeetingMember.class));
    }
}
