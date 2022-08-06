package com.social.moinda.core.domains.meeting.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.moinda.core.domains.meeting.dto.MeetingDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.social.moinda.core.domains.group.entity.QGroup.group;
import static com.social.moinda.core.domains.meetingmember.entity.QMeetingMember.meetingMember;

@Repository
public class MeetingQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    private final QMeeting meeting;

    public MeetingQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Meeting.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.meeting = QMeeting.meeting;
    }

    public Optional<Meeting> findMeetingById(Long meetingId) {
        Meeting entity = getMeeting(meetingId);
        return Optional.ofNullable(entity);
    }

    public Optional<Meeting> findById(Long meetingId) {
        return Optional.ofNullable(getMeeting(meetingId));
    }

    private Meeting getMeeting(Long meetingId) {
        return jpaQueryFactory.selectFrom(meeting).distinct()
                .leftJoin(meeting.meetingMember, meetingMember).fetchJoin()
                .leftJoin(meeting.group, group).fetchJoin()
                .where(meeting.id.eq(meetingId))
                .fetchOne();
    }

    public List<MeetingDto> findMeetingsByGroupId(Long groupId) {
        List<Meeting> meetingList = jpaQueryFactory.selectFrom(meeting).distinct()
                .leftJoin(meeting.group, group).fetchJoin()
                .where(meeting.group.id.eq(groupId))
                .orderBy(meeting.id.desc())
                .fetch();

        return meetingList.stream()
                .map(Meeting::bindToMeetingDto)
                .collect(Collectors.toList());
    }
}
