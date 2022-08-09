package com.social.moinda.api.member.service;


import com.social.moinda.api.member.exception.NotFoundMemberException;
import com.social.moinda.core.domains.member.dto.MemberDetails;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public MemberDetails getMemberWithGroupInfo(Long memberId) {
        Member entity = memberQueryRepository.findMemberDetailsById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        return new MemberDetails(entity.bindToMemberDto(),entity.bindToGroupDtoList());
    }
}
