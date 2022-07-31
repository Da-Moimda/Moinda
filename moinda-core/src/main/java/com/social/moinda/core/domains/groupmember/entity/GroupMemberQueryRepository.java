package com.social.moinda.core.domains.groupmember.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

import static com.social.moinda.core.domains.group.entity.QGroup.group;
import static com.social.moinda.core.domains.member.entity.QMember.member;

@Repository
public class GroupMemberQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    private QGroupMember groupMember;

    public GroupMemberQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(GroupMember.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.groupMember = QGroupMember.groupMember;
    }

    public Optional<GroupMember> findGroupMemberById(Long groupId, Long memberId) {
        GroupMember entity = getGroupMember(groupId, memberId);
        return Optional.ofNullable(entity);
    }

    public boolean isJoinedGroupMember(Long groupId, Long memberId) {
        GroupMember entity = getGroupMember(groupId, memberId);
        return !ObjectUtils.isEmpty(entity);
    }

    private GroupMember getGroupMember(Long groupId, Long memberId) {
        return jpaQueryFactory.selectFrom(groupMember)
                .leftJoin(groupMember.group, group).fetchJoin()
                .leftJoin(groupMember.member, member).fetchJoin()
                .where(groupMember.group.id.eq(groupId))
                .where(groupMember.member.id.eq(memberId))
                .fetchOne();
    }
}
