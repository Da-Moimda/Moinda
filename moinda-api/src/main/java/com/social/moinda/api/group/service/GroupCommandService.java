package com.social.moinda.api.group.service;

import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.group.dto.GroupJoinRequest;
import com.social.moinda.api.group.exception.NotEnabledJoinStatusException;
import com.social.moinda.api.group.exception.NotFoundGroupException;
import com.social.moinda.api.group.exception.RegisteredGroupNameException;
import com.social.moinda.api.groupmember.exception.AlreadyJoinGroupMemberException;
import com.social.moinda.api.member.exception.NotFoundMemberException;
import com.social.moinda.core.domains.group.dto.GroupCreateResponse;
import com.social.moinda.core.domains.group.dto.GroupJoinResponse;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupQueryRepository;
import com.social.moinda.core.domains.groupmember.entity.GroupMember;
import com.social.moinda.core.domains.groupmember.entity.GroupMemberQueryRepository;
import com.social.moinda.core.domains.groupmember.entity.GroupMemberRepository;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupCommandService {

    private final GroupQueryRepository groupQueryRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupMemberQueryRepository groupMemberQueryRepository;

    public GroupCreateResponse create(GroupCreateDto groupCreateDto) {
        Member member = existMember(groupCreateDto.getMemberId());

        existByGroupName(groupCreateDto.getName());

        Group group = groupCreateDto.bindToEntity();
        GroupMember groupMember = new GroupMember(group, member);

        groupMemberRepository.save(groupMember);

        return group.bindToCreateResponse(member.bindToMemberDto());
    }

    public GroupJoinResponse joinGroup(GroupJoinRequest joinRequest) {
        Long groupId = joinRequest.getGroupId();
        Long memberId = joinRequest.getMemberId();

        isJoinedGroupMember(groupId, memberId);

        Group group = groupQueryRepository.findById(groupId)
                .orElseThrow(NotFoundGroupException::new);

        isEnabledJoinStatusByCapacity(group);

        Member member = memberQueryRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        GroupMember groupMember = new GroupMember(group, member);

        groupMemberRepository.save(groupMember);

        return groupMember.convertToJoinResponse(memberId);
    }

    @Transactional(readOnly = true)
    public Member existMember(Long memberId) {
        return memberQueryRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
    }

    @Transactional(readOnly = true)
    public void existByGroupName(String groupName) {
        if(groupQueryRepository.existsByName(groupName)) {
            throw new RegisteredGroupNameException();
        }
    }

    @Transactional(readOnly = true)
    public void isJoinedGroupMember(Long groupId, Long memberId) {
        if(groupMemberQueryRepository.isJoinedGroupMember(groupId, memberId)) {
            throw new AlreadyJoinGroupMemberException();
        }
    }

    private void isEnabledJoinStatusByCapacity(Group group) {
        if(!group.enabledJoin()) {
           throw new NotEnabledJoinStatusException();
        }
    }
}
