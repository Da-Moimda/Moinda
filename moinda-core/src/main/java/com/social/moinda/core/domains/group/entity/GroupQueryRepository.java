package com.social.moinda.core.domains.group.entity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.group.dto.Groups;
import com.social.moinda.core.domains.member.entity.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    // TODO : Service 계층에서 ?
        Groups groups = new Groups(groupList);

        return groups.getGroupDtoList();
    }

    public List<GroupDto> findAllByNameContains(String keyword) {

        List<Group> groupList = jpaQueryFactory.selectFrom(group).distinct()
                .leftJoin(group.groupMember, groupMember).fetchJoin()
                .where(group.id.eq(groupMember.group.id))
                .where(containGroup(keyword))
                .groupBy(group.id)
                .fetch();

        // TODO : Service 계층에서 ?
        Groups groups = new Groups(groupList);

        return groups.getGroupDtoList();
    }

    private BooleanExpression containGroup(String keyword) {
        return containGroupName(keyword)
                .or(containGroupIntroduce(keyword))
                .or(containGroupLocationSi(keyword))
                .or(containGroupLocationDo(keyword))
                .or(eqGroupConcern(keyword));
    }

    private BooleanExpression containGroupLocationSi(String keyword) {
        return keyword == null ? null : group.location.locationSi.contains(keyword);
    }

    private BooleanExpression containGroupLocationDo(String keyword) {
        return keyword == null ? null : group.location.locationDo.contains(keyword);
    }

    private BooleanExpression containGroupName(String keyword) {
        return keyword == null ? null : group.name.contains(keyword);
    }

    private BooleanExpression containGroupIntroduce(String keyword) {
        return keyword == null ? null : group.introduce.contains(keyword);
    }

    private BooleanExpression eqGroupConcern(String keyword) {
        return keyword == null ? null : group.concern.stringValue().equalsIgnoreCase(keyword);
    }
}
