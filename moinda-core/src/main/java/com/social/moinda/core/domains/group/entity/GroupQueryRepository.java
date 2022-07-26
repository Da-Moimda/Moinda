package com.social.moinda.core.domains.group.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.member.entity.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.social.moinda.core.domains.groupmember.entity.QGroupMember.groupMember;

@Repository
public class GroupQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    private QGroup group;

    public GroupQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.group = QGroup.group;
    }

    public List<GroupDto> findGroupAll() {
        List<Group> groupList = jpaQueryFactory.selectFrom(group).distinct()
                .leftJoin(group.groupMember, groupMember).fetchJoin()
                .where(group.id.eq(groupMember.group.id))
                .groupBy(group.id)
                .fetch();

        for ( Group entity : groupList ){
            System.out.println("entity : " + entity);
            System.out.println("entity : " + entity.getGroupMember().size());
        }

        return groupList.stream().map(Group::bindToGroupDto).collect(Collectors.toList());
    }

}
