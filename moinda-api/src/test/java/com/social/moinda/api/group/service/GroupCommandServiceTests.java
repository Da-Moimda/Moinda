package com.social.moinda.api.group.service;


import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GroupCommandServiceTests {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupCommandService groupCommandService;

    @DisplayName("모임 생성하기 - 성공")
    @Test
    void createSuccessTest() {
        // 그룹이름, 소개, 위치, 모임주제, 정원
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
        Member member = new Member("dssd@dsds.com","dsds","1212", group);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(memberRepository.save(any(Member.class))).willReturn(member);

        groupCommandService.create(groupCreateDto);

        then(memberRepository).should(times(1)).findById(anyLong());
        then(memberRepository).should(times(1)).save(any(Member.class));
        assertThatNoException();
    }

    @DisplayName("모임 생성 (사용자가 없어서 실패한다.) - 실패")
    @Test
    void createFailTest() {
        // 그룹이름, 소개, 위치, 모임주제, 정원
        GroupCreateDto groupCreateDto = new GroupCreateDto(
                1L,
                "모임입니다!!",
                "소개글입니다.",
                "경기도",
                "부천시",
                "FREE",
                300
        );

        given(memberRepository.findById(anyLong())).willThrow(new IllegalStateException("없는 사용자입니다."));

        assertThatThrownBy(() -> groupCommandService.create(groupCreateDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("없는 사용자입니다.");

        then(memberRepository).shouldHaveNoMoreInteractions();
        then(groupRepository).shouldHaveNoMoreInteractions();
    }

    @DisplayName("그룹 해체하기 - 성공")
    @Test
    void removeGroupSuccessTest() {

        Member member = new Member("user1@eamil.com", "user1", "12121212", null);

        doNothing().when(groupRepository).deleteById(anyLong());
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        groupCommandService.remove(anyLong(), 1L);

        then(memberRepository).should(times(1)).findById(anyLong());
        then(groupRepository).should(times(1)).deleteById(anyLong());
        assertThatNoException();
    }

}
