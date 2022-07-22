package com.social.moinda.api.member.service;


import com.social.moinda.api.member.exception.NotFoundMemberException;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberDto getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);

        return member.bindToMemberDto();
    }

}
