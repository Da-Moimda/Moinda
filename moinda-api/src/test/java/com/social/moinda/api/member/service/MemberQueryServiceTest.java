package com.social.moinda.api.member.service;

import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.member.dto.MemberDetails;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

    @Mock
    private MemberQueryRepository memberQueryRepository;

    @InjectMocks
    private MemberQueryService memberQueryService;

    @DisplayName("사용자가 가진 그룹정보 같이 가져오기")
    @Test
    void successGetMemberAndGroupInfo() {
        Member member = new Member("user1@email.com", "user1", "안녕하세요.", "12121212");

        MemberDetails memberDetails = new MemberDetails(member.bindToMemberDto(), List.of(
                new GroupDto(1L, "그룹1", "부천시", "FREE", 20),
                new GroupDto(2L, "그룹2", "시흥시", "STUDY", 30),
                new GroupDto(3L, "그룹3", "마포구", "FREE", 11)
        ));

        given(memberQueryRepository.findMemberDetailsById(anyLong())).willReturn(Optional.of(member));

        memberQueryService.getMemberWithGroupInfo(1L);

        then(memberQueryRepository).should(times(1)).findMemberDetailsById(anyLong());

    }
}