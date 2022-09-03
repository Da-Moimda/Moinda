package com.social.moinda.api.group.service;

import com.social.moinda.core.domains.pagination.PagingRequest;
import com.social.moinda.core.domains.group.dto.GroupDetails;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupQueryRepository;
import com.social.moinda.core.domains.meeting.dto.MeetingDto;
import com.social.moinda.core.domains.meeting.entity.MeetingQueryRepository;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.core.domains.pagination.PagingResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupQueryServiceTests {

    @Mock
    private GroupQueryRepository groupQueryRepository;

    @Mock
    private MeetingQueryRepository meetingQueryRepository;

    @InjectMocks
    private GroupQueryService groupQueryService;

    @DisplayName("전체 그룹 조회 - 성공")
    @Test
    void findAllSuccessTest() {

        List<GroupDto> dtoList = List.of(
                new GroupDto(1L, "그룹1", "부천시", "FREE", 20),
                new GroupDto(2L, "그룹2", "시흥시", "STUDY", 30),
                new GroupDto(3L, "그룹3", "마포구", "FREE", 11)
        );

        given(groupQueryRepository.findGroups()).willReturn(dtoList);

        List<GroupDto> resultList = groupQueryService.searchGroups();

        then(groupQueryRepository).should(times(1)).findGroups();
        assertThat(resultList.size()).isEqualTo(dtoList.size());
        assertThatNoException();
    }

    @DisplayName("특정 그룹명 검색 - 성공")
    @Test
    void searchGroupSuccessTest() {

        List<GroupDto> dtoList = List.of(
                new GroupDto(1L, "그룹1", "부천시", "FREE", 20),
                new GroupDto(2L, "그룹2", "시흥시", "STUDY", 30),
                new GroupDto(3L, "그룹3", "마포구", "FREE", 11)
        );

        given(groupQueryRepository.findAllByNameContains(anyString())).willReturn(dtoList);

        groupQueryService.searchGroups(anyString());

        then(groupQueryRepository).should(times(1)).findAllByNameContains(anyString());

    }

    @DisplayName("해당 그룹의 상세 정보 조회 성공하기")
    @Test
    void getGroupDetailsSuccessTest() {
        GroupDetails groupDetails = new GroupDetails(
                1L, "그룹", "그룹입니다."
                ,List.of(new MemberDto(1L, "user1@email.com", "user1", "안녕하세요!!"),
                new MemberDto(2L, "user2@email.com", "user2", "안녕하세요!!")),
                List.of(new MeetingDto(1L, "스타벅스", 5000, LocalDateTime.now()),
                        new MeetingDto(2L, "투썸", 4500, LocalDateTime.now())));

        List<MeetingDto> meetingDtoList = List.of(new MeetingDto(1L, "스타벅스", 5000, LocalDateTime.now()),
                new MeetingDto(2L, "투썸", 4500, LocalDateTime.now()));

        Group group = new Group("그룹1", "그룹입니다1", null, null, 30);

        given(groupQueryRepository.findById(anyLong())).willReturn(Optional.of(group));
        given(meetingQueryRepository.findMeetingsByGroupId(anyLong())).willReturn(meetingDtoList);

        groupQueryService.getGroupDetails(anyLong());

        then(groupQueryRepository).should(times(1)).findById(anyLong());
        then(meetingQueryRepository).should(times(1)).findMeetingsByGroupId(anyLong());
    }

    @DisplayName("전체 모임 목록 페이징 처리 하기")
    @Test
    void getGroupsWithPagingCustomTest() {
        /*
            1. PageRequest 관련 DTO를 받는다.
            2. PageRequest에 Pageable 관련 객체를 만든다.
            3. 조회시 Pagealbe을 통해서 페이징관련 목록을 받는다.
            4. 서비스 계층에서 변환시킨다?
         */

        List<GroupDto> dtoList = LongStream.rangeClosed(1, 40)
                .mapToObj(idx ->
                        new GroupDto(idx, "그룹" + idx, "부천시", "FREE", 30))
                .sorted(Comparator.comparing(GroupDto::getGroupId).reversed())
                .collect(Collectors.toList());

        final int page = 1;
        final int size = 10;

        PagingRequest pagingRequest = new PagingRequest(page, size, "groupId");
        Page<GroupDto> dtoPage = new PageImpl<>(dtoList, pagingRequest.getPageable(), dtoList.size());

        given(groupQueryRepository.findGroupsWithPagingCustom(any(PagingRequest.class)))
                .willReturn(dtoPage);

        PagingResult<GroupDto> pagingResult = groupQueryService.displayGroupsWithPaging(pagingRequest);

        System.out.println("pagingResult : " + pagingResult);

        then(groupQueryRepository).should(times(1))
                .findGroupsWithPagingCustom(any(PagingRequest.class));

        assertThat(pagingResult.getPage()).isEqualTo(page);
        assertThat(pagingResult.getSize()).isEqualTo(size);
    }
}
