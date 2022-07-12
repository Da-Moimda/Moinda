package com.social.moinda.api.member.service;

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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberQueryService memberQueryService;

    @DisplayName("사용자 찾기 - 성공")
    @Test
    void successGetMemberInfo() {
        Member member = new Member("user1@email.com", "user1", "12121212");

        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

        memberQueryService.getMemberInfo(member.getEmail());

        assertDoesNotThrow(() -> IllegalArgumentException.class);
    }

    @DisplayName("사용자 찾기 - 실패")
    @Test
    void failGetMemberInfo() {
        String email = "user1@email.com";

        given(memberRepository.findByEmail(email)).willThrow(IllegalStateException.class);

        assertThatThrownBy(() -> memberQueryService.getMemberInfo(email));

    }
}