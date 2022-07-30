package com.social.moinda.api.groupmember.exception;

public class NotJoinedGroupMemberException extends RuntimeException {
    public NotJoinedGroupMemberException() {
        this("해당 그룹에 가입한 사용자가 아닙니다.");
    }

    // TODO : 변경필요.. 안쓰이는 중
    public NotJoinedGroupMemberException(Long groupId, Long memberId) {
        this("해당 그룹에 가입한 사용자가 아닙니다. groupId : " + groupId + ", memberId : " + memberId);
    }

    public NotJoinedGroupMemberException(String message) {
        super(message);
    }
}
