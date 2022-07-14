package com.social.moinda.api.group.service;

import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.core.domains.group.dto.GroupCreateResponse;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupCommandService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    public GroupCreateResponse create(GroupCreateDto groupCreateDto) {
        // TODO : Service 를 가져오는 것으로 변경 ?? , 예외코드 커스텀 필요
        Member member = findMember(groupCreateDto.getMemberId());

        Group group = groupCreateDto.bindToEntity();

        member.setGroup(group);
        //TODO : Member에서 영속성전이로 MemberRepository에서 Save하는 것으로 바꾸기. -> Test Code 변경필요.
        memberRepository.save(member);

        GroupCreateResponse groupCreateResponse = group.bindToCreateResponse(member.bindToMemberDto());
        return groupCreateResponse;
    }

    @Transactional(readOnly = true)
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("없는 사용자입니다."));
    }

    /*
        TODO : 양방향 매핑으로 변경시 로직수정 필요
     */
    public void remove(Long groupId, Long memberId) {
        Member member = findMember(memberId);
        groupRepository.deleteById(groupId); // TODO : 추가적인 select 쿼리발생 -> 벌크성 쿼리로 변경
        member.setGroup(null);
    }
}
