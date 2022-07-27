package com.social.moinda.core.domains.group.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class GroupDto {

    private Long groupId;
    private String groupName;
    private String locationSi;
    private String concern;
    private int userNum;

    // TODO : 가입한 인원을 표시해야 함.

    public GroupDto(Long groupId, String groupName, String locationSi, String concern, int userNum) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.locationSi = locationSi;
        this.concern = concern;
        this.userNum = userNum;
    }
}
