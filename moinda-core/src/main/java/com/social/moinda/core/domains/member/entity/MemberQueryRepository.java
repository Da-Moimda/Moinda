package com.social.moinda.core.domains.member.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.moinda.core.domains.member.dto.MemberDetails;
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

    // 사용자의 정보 조회 -> 가입한 그룹 등등 포함
    public MemberDetails findMemberDetails(Long memberId) {
        Member entity = getMember(memberId);

        return new MemberDetails(entity.bindToMemberDto(),entity.bindToGroupDtoList());
    }

    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(getMember(memberId));
    }

    private Member getMember(Long memberId) {
        return jpaQueryFactory
                .selectDistinct(member)
                .from(member)
                .leftJoin(member.groupMember, groupMember).fetchJoin()
                .leftJoin(groupMember.group, group).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchFirst();
    }
}
