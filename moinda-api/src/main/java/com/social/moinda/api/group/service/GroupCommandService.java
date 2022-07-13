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
        // TODO : Service 를 가져오는 것으로 변경 ??
        Member member = memberRepository.findById(groupCreateDto.getMemberId())
                .orElseThrow(() -> new IllegalStateException("없는 사용자입니다."));

        Group entity = groupCreateDto.bindToEntity();

        //TODO : Member에서 영속성전이로 MemberRepository에서 Save하는 것으로 바꾸기. -> Test Code 변경필요.
        entity = groupRepository.save(entity);
//        memberRepository.save(member);
        System.out.println("member : " + member);
        System.out.println("entity : " + entity);

        GroupCreateResponse groupCreateResponse = entity.bindToCreateResponse(member.bindToMemberDto());
        System.out.println("res : " + groupCreateResponse);
        return groupCreateResponse;
    }

}
