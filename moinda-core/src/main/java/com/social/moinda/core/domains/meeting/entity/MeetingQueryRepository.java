package com.social.moinda.core.domains.meeting.entity;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * TODO:
 *  22-07-28 15:10
 *  Meeting Querydsl 작성 도중 Meeting - Member 연관관계 변경 필요.
 */

@Repository
public class MeetingQueryRepository extends QuerydslRepositorySupport {
    public MeetingQueryRepository() {
        super(Meeting.class);
    }

    public Optional<Meeting> findMeeting(Long meetingId) {

        return null;
    }
}
