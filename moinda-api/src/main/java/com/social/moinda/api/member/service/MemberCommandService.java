package com.social.moinda.api.member.service;

import com.social.moinda.api.member.dto.MemberCreateDto;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public MemberDto create(MemberCreateDto dto) {

        isExistMember(dto);

        Member savedEntity = memberRepository.save(dto.bindEntity());

        return savedEntity.bindToMemberDto();
    }

    public boolean remove(String email) {
       return memberRepository.deleteByEmail(email);
    }

    @Transactional(readOnly = true)
    public void isExistMember(MemberCreateDto dto) {
        if(memberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("존재하는 이메일입니다.");
        }
    }
}
