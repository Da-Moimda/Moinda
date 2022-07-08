package com.social.talk.api.member.service;

import com.social.talk.api.member.dto.MemberCreateDto;
import com.social.talk.core.domains.member.dto.MemberDto;
import com.social.talk.core.domains.member.entity.Member;
import com.social.talk.core.domains.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public MemberDto create(MemberCreateDto dto) {

        isExistMember(dto.getEmail());

        Member savedEntity = memberRepository.save(dto.bindEntity());

        return savedEntity.bindToMemberDto();
    }


    public boolean remove(String email) {
       return memberRepository.deleteByEmail(email);
    }

    @Transactional(readOnly = true)
    public void isExistMember(String email) {
        if (memberRepository.existByEmail(email)) {
            throw new IllegalStateException("존재하는 이메일입니다.");
        }
    }
}
