package com.social.moinda.core.domains.member.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.moinda.core.domains.member.dto.MemberDetails;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

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
        Member entity = jpaQueryFactory
                .selectDistinct(member)
                .from(member)
                .leftJoin(member.groupMember, groupMember).fetchJoin()
                .leftJoin(groupMember.group, group).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchFirst();

        // TODO : 사용자 이름과 이메일을 보여주는 것으로 변경할 경우 bind 메서드로 리팩토링
        return new MemberDetails(memberId,entity.bindToGroupDtoList());
    }
}
