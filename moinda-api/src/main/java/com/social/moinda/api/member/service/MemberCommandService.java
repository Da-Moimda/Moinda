package com.social.moinda.api.member.service;

import com.social.moinda.api.member.dto.SignupRequest;
import com.social.moinda.api.member.exception.RegisteredEmailException;
import com.social.moinda.core.domains.member.dto.SignupResponse;
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

    public SignupResponse create(SignupRequest dto) {

        isExistMember(dto);

        Member savedEntity = memberRepository.save(dto.bindEntity());

        return savedEntity.bindToSignupResponse();
    }

    public boolean remove(String email) {
       return memberRepository.deleteByEmail(email);
    }

    @Transactional(readOnly = true)
    public void isExistMember(SignupRequest dto) {
        if(memberRepository.existsByEmail(dto.getEmail())) {
            throw new RegisteredEmailException();
        }
    }
}
