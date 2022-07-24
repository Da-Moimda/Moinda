package com.social.moinda.api.group.service;

import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.group.exception.RegisteredGroupNameException;
import com.social.moinda.api.member.exception.NotFoundMemberException;
import com.social.moinda.core.domains.group.dto.GroupCreateResponse;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import com.social.moinda.core.domains.groupmember.entity.GroupMember;
import com.social.moinda.core.domains.groupmember.entity.GroupMemberRepository;
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
    private final GroupMemberRepository groupMemberRepository;

    public GroupCreateResponse create(GroupCreateDto groupCreateDto) {
        // TODO : Service 를 가져오는 것으로 변경 ?? , 예외코드 커스텀 필요
        Member member = existMember(groupCreateDto.getMemberId());
        
        existByGroupName(groupCreateDto.getName());

        Group group = groupCreateDto.bindToEntity();
        GroupMember groupMember = new GroupMember(group, member);

        groupMemberRepository.save(groupMember);

        GroupCreateResponse groupCreateResponse = group.bindToCreateResponse(member.bindToMemberDto());
        return groupCreateResponse;
    }

    /*
    TODO : 양방향 매핑으로 변경시 로직수정 필요
 */
    public void remove(Long groupId, Long memberId) {
        existMember(memberId);
        groupRepository.deleteById(groupId); // TODO : 추가적인 select 쿼리발생 -> 벌크성 쿼리로 변경
    }

    @Transactional(readOnly = true)
    public Member existMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
    }

    @Transactional(readOnly = true)
    public void existByGroupName(String groupName) {
        if(groupRepository.existsByName(groupName)) {
            throw new RegisteredGroupNameException();
        }
    }
}
