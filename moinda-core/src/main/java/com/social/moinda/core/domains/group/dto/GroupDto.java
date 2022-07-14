package com.social.moinda.core.domains.group.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class GroupDto {

    private Long groupId;
    private String groupName;
    private String locationSi;
    private String concern;
    private int userNum;

}
