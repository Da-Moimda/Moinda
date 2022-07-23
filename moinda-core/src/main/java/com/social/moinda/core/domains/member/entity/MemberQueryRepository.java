package com.social.moinda.core.domains.member.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.moinda.core.domains.member.dto.MemberDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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

    // 사용자가 가입한 모든 그룹을 조회한다.
    public List<MemberDto> findMemberWithRegisteredGroups(Long memberId) {
        List<Member> entityList = jpaQueryFactory.select(member)
                .from(member)
                .leftJoin(member.groupMember, groupMember).fetchJoin()
                .where(member.id.eq(memberId))
                .fetch();

        // TODO : must be Refactoring ( DTO )
        return entityList.stream().map(findMember -> {
            return new MemberDto(findMember.getId(), findMember.getEmail(), findMember.getName());
        }).collect(Collectors.toList());
    }
}
