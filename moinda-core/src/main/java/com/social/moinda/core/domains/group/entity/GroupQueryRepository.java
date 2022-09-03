package com.social.moinda.core.domains.group.entity;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.group.dto.QGroupDto;
import com.social.moinda.core.domains.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.social.moinda.core.domains.groupmember.entity.QGroupMember.groupMember;
import static com.social.moinda.core.domains.member.entity.QMember.member;

@Repository
public class GroupQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    private final QGroup group;

    public GroupQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.group = QGroup.group;
    }

    public Optional<Group> findById(Long groupId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(group).distinct()
                .leftJoin(group.groupMember, groupMember).fetchJoin()
                .leftJoin(groupMember.member, member).fetchJoin()
                .where(group.id.eq(groupId))
                .fetchOne());
    }

    public List<GroupDto> findGroups() {
        List<Group> groups = jpaQueryFactory.selectFrom(group).distinct()
                .leftJoin(group.groupMember, groupMember).fetchJoin()
                .leftJoin(groupMember.member, member).fetchJoin()
                .fetch();

        return bindToDtoList(groups);
    }

    public Page<GroupDto> findGroupsWithPaging(Pageable pageable) {
        List<GroupDto> groups = jpaQueryFactory.selectFrom(group)
                .leftJoin(group.groupMember, groupMember)
                .leftJoin(groupMember.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                /*
                        firstResult/maxResults specified with collection fetch; applying in memory!
                        1. DTO로 변환해서 반환하는 방법.
                        2. ManyToOne의 주체에서 쿼리하는 방법.
                 */
                .transform(
                        GroupBy.groupBy(group.id)
                                .list(new QGroupDto(
                                        group.id,
                                        group.name,
                                        group.location.locationSi,
                                        group.concern.stringValue(),
                                        group.groupMember.size()
                                        ))
                );

        long total = groups.size();

        return new PageImpl<>(groups, pageable, total);
    }

    public Page<GroupDto> findGroupsWithPagingCustom(Pageable pageable) {
        List<GroupDto> groups = jpaQueryFactory.selectFrom(group)
                .leftJoin(group.groupMember, groupMember)
                .leftJoin(groupMember.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                /*
                        firstResult/maxResults specified with collection fetch; applying in memory!
                        1. DTO로 변환해서 반환하는 방법.
                        2. ManyToOne의 주체에서 쿼리하는 방법.
                 */
                .transform(
                        GroupBy.groupBy(group.id)
                                .list(new QGroupDto(
                                        group.id,
                                        group.name,
                                        group.location.locationSi,
                                        group.concern.stringValue(),
                                        group.groupMember.size()
                                ))
                );

        long total = groups.size();

        return new PageImpl<>(groups, pageable, total);
    }

    public List<GroupDto> findAllByNameContains(String keyword) {

        List<Group> groups = jpaQueryFactory.selectFrom(group).distinct()
                .leftJoin(group.groupMember, groupMember).fetchJoin()
                .leftJoin(groupMember.member, member).fetchJoin()
                .where(group.id.eq(groupMember.group.id))
                .where(containGroup(keyword))
                .fetch();

        return bindToDtoList(groups);
    }

    private List<GroupDto> bindToDtoList(List<Group> groups) {
        return groups.stream()
                .map(Group::bindToGroupDto)
                .collect(Collectors.toList());
    }

    public boolean existsByName(String name) {
        Group entity = jpaQueryFactory.selectFrom(group).distinct()
                .leftJoin(group.groupMember, groupMember).fetchJoin()
                .where(group.name.equalsIgnoreCase(name))
                .fetchOne();
        return !ObjectUtils.isEmpty(entity);
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
