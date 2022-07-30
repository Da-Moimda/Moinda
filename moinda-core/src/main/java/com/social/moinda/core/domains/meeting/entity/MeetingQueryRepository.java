package com.social.moinda.core.domains.meeting.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.social.moinda.core.domains.meeting.dto.MeetingDetails;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.social.moinda.core.domains.group.entity.QGroup.group;
import static com.social.moinda.core.domains.meetingmember.entity.QMeetingMember.meetingMember;

@Repository
public class MeetingQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    private QMeeting meeting;

    public MeetingQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Meeting.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.meeting = QMeeting.meeting;
    }

    public Optional<MeetingDetails> findMeeting(Long meetingId) {
        Meeting entity = getMeeting(meetingId);

        MeetingDetails meetingDetails = entity.bindToMeetingDetails();

        return Optional.ofNullable(meetingDetails);
    }

    public Optional<Meeting> findById(Long meetingId) {
        return Optional.ofNullable(getMeeting(meetingId));
    }

    private Meeting getMeeting(Long meetingId) {
        return jpaQueryFactory.selectFrom(meeting).distinct()
                .leftJoin(meeting.meetingMember, meetingMember).fetchJoin()
                .leftJoin(meeting.group, group).fetchJoin()
                .where(meeting.id.eq(meetingId))
                .fetchFirst();
    }
}
