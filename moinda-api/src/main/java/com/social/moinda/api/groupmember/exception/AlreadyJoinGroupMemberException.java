package com.social.moinda.api.groupmember.exception;

public class AlreadyJoinGroupMemberException extends RuntimeException {
    public AlreadyJoinGroupMemberException() {
        this("이미 가입한 그룹입니다.");
    }

    public AlreadyJoinGroupMemberException(String message) {
        super(message);
    }
}
