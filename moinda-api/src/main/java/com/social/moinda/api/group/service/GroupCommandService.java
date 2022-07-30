package com.social.moinda.api.group.service;

import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.group.dto.GroupJoinRequest;
import com.social.moinda.api.group.exception.NotFoundGroupException;
import com.social.moinda.api.group.exception.RegisteredGroupNameException;
import com.social.moinda.api.groupmember.exception.AlreadyJoinGroupMemberException;
import com.social.moinda.api.member.exception.NotFoundMemberException;
import com.social.moinda.core.domains.group.dto.GroupCreateResponse;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupQueryRepository;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import com.social.moinda.core.domains.groupmember.entity.GroupMember;
import com.social.moinda.core.domains.groupmember.entity.GroupMemberQueryRepository;
import com.social.moinda.core.domains.groupmember.entity.GroupMemberRepository;
import com.social.moinda.core.domains.member.entity.Member;
import com.social.moinda.core.domains.member.entity.MemberQueryRepository;
import com.social.moinda.core.domains.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupCommandService {

    private final GroupRepository groupRepository;
    private final GroupQueryRepository groupQueryRepository;
    private final MemberRepository memberRepository;
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

    /*
    TODO : 양방향 매핑으로 변경시 로직수정 필요
 */
    public void remove(Long groupId, Long memberId) {
        existMember(memberId);
        // TODO : Querydsl
        groupRepository.deleteById(groupId); // TODO : 추가적인 select 쿼리발생 -> 벌크성 쿼리로 변경
    }

    /**
     * TODO : 반환타입 설정
     *  인원 수 체크 필요.
     */
    public void joinGroup(GroupJoinRequest joinRequest) {
        Long groupId = joinRequest.getGroupId();
        Long memberId = joinRequest.getMemberId();

        isJoinedGroupMember(groupId, memberId);

        Group group = groupQueryRepository.findById(groupId)
                .orElseThrow(NotFoundGroupException::new);

        Member member = memberQueryRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        GroupMember groupMember = new GroupMember(group, member);

        groupMemberRepository.save(groupMember);
    }

    @Transactional(readOnly = true)
    public Member existMember(Long memberId) {
        // TODO : Querydsl
        return memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
    }

    @Transactional(readOnly = true)
    public void existByGroupName(String groupName) {
        // TODO : Querydsl
        if(groupRepository.existsByName(groupName)) {
            throw new RegisteredGroupNameException();
        }
    }

    @Transactional(readOnly = true)
    public void isJoinedGroupMember(Long groupId, Long memberId) {
        if(groupMemberQueryRepository.isJoinedGroupMember(groupId, memberId)) {
            throw new AlreadyJoinGroupMemberException();
        }
    }
}
