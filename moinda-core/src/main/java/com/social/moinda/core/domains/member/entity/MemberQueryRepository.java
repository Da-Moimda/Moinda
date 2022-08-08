package com.social.moinda.core.domains.member.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.social.moinda.core.domains.group.entity.QGroup.group;
import static com.social.moinda.core.domains.groupmember.entity.QGroupMember.groupMember;

@Repository
public class MemberQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMember member;

    public MemberQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.member = QMember.member;
    }

    /**
     * 본인에 대한 정보를 확인시 사용.
     */
    public Optional<Member> findMemberDetailsById(Long memberId) {
        return Optional.ofNullable(getMember(memberId));
    }

    /**
     * 다른 비즈니스 로직에서 순수 조회 용도
     */
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(getMember(memberId));
    }

    private Member getMember(Long memberId) {
        return jpaQueryFactory
                .selectFrom(member).distinct()
                .leftJoin(member.groupMember, groupMember).fetchJoin()
                .leftJoin(groupMember.group, group).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne();
    }
}
