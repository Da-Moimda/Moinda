package com.social.moinda.api.member.service;

import com.social.moinda.api.member.dto.MemberCreateDto;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class MemberCommandServiceTests {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberCommandService memberCommandService;

    @DisplayName("회원 등록 - 성공")
    @Test
    void createSuccessTest() {
        // Given
        MemberCreateDto createDto = new MemberCreateDto("user1@email.com", "user1", "12121212", "12121212");

        Member member = createDto.bindEntity();
        given(memberRepository.existsByEmail(createDto.getEmail())).willReturn(false);
        given(memberRepository.save(any())).willReturn(member);

        memberCommandService.create(createDto);

        then(memberRepository).should(times(1)).existsByEmail(createDto.getEmail());
        then(memberRepository).should(times(1)).save(any(Member.class));
    }

    @DisplayName("회원 등록시 이미 사용자가 등록되어있다. - 실패")
    @Test
    void createFailTest() {
        MemberCreateDto createDto = new MemberCreateDto("user1@email.com", "user1", "12121212", "12121212");
        Member member = createDto.bindEntity();

        given(memberRepository.existsByEmail(anyString())).willReturn(true);

        assertThatThrownBy(() -> memberCommandService.create(createDto))
                .isInstanceOf(IllegalStateException.class);

        then(memberRepository).should(times(1)).existsByEmail(createDto.getEmail());
        then(memberRepository).should(times(0)).save(any(Member.class));
    }

    @DisplayName("회원 삭제 - 성공")
    @Test
    void removeSuccessTest() {
        String email = "user1@email.com";

        given(memberRepository.deleteByEmail(email)).willReturn(true);

        boolean result = memberCommandService.remove(email);

        assertThat(result).isTrue();
    }

    @DisplayName("회원 삭제 - 실패")
    @Test
    void removeFailTest() {
        String email = "user1@email.com";

        given(memberRepository.deleteByEmail(email)).willReturn(false);

        boolean result = memberCommandService.remove(email);

        assertThat(result).isFalse();
    }
}
