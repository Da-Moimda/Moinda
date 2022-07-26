package com.social.moinda.core.domains.group.entity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    public List<GroupDto> findAllByNameContains(String keyword) {

        List<Group> groupList = jpaQueryFactory.selectFrom(group).distinct()
                .leftJoin(group.groupMember, groupMember).fetchJoin()
                .where(group.id.eq(groupMember.group.id))
                .where(searchGroup(keyword))
                .groupBy(group.id)
                .fetch();

        System.out.println("keyword : " + keyword);

        return groupList.stream().map(Group::bindToGroupDto).collect(Collectors.toList());
    }

    // TODO : 테스트를 좀더 해볼 필요가 있음.
    private BooleanBuilder searchGroup(String keyword) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.or(searchGroupName(keyword))
                .or(searchGroupIntroduce(keyword))
                .or(searchGroupLocationSi(keyword))
                .or(searchGroupConcern(keyword))
                .or(searchGroupLocationDo(keyword));

        return builder;
    }

    private BooleanExpression searchGroupLocationSi(String keyword) {
        return keyword == null ? null : group.location.locationSi.contains(keyword);
    }

    private BooleanExpression searchGroupLocationDo(String keyword) {
        return keyword == null ? null : group.location.locationDo.contains(keyword);
    }

    private BooleanExpression searchGroupName(String keyword) {
        return keyword == null ? null : group.name.contains(keyword);
    }

    private BooleanExpression searchGroupIntroduce(String keyword) {
        return keyword == null ? null : group.introduce.contains(keyword);
    }


    // TODO : 대소문자 구분 X 비교로 변경
    private BooleanExpression searchGroupConcern(String keyword) {
        return keyword == null ? null : group.concern.stringValue().equalsIgnoreCase(keyword);
    }
}
