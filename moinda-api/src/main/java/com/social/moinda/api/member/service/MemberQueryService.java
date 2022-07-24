package com.social.moinda.api.member.service;


import com.social.moinda.api.member.exception.NotFoundMemberException;
import com.social.moinda.core.domains.member.dto.MemberDetails;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberQueryRepository;
import com.social.moinda.core.domains.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    public MemberDto getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);

        return member.bindToMemberDto();
    }

    public MemberDetails getMemberWithGroupInfo(Long memberId) {
        return memberQueryRepository.findMemberDetails(memberId);
    }
}
