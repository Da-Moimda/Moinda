package com.social.moinda.api.group.dto;


import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupConcern;
import com.social.moinda.core.domains.group.entity.GroupLocation;
import com.social.moinda.core.domains.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupCreateDto {

    private Long memberId;
    private String name;
    private String introduce;
    // TODO : 네이밍 변경필요
    private String locationDo;
    private String locationSi;

    private String concern;
    private int capacity;


    // TODO : GrouopConcern 매개변수를 받아야 한다.
    public GroupCreateDto(Long memberId,
                          String name,
                          String introduce,
                          String locationDo,
                          String locationSi,
                          String concern,
                          int capacity) {
        this.memberId = memberId;
        this.name = name;
        this.introduce = introduce;
        this.locationDo = locationDo;
        this.locationSi = locationSi;
        this.concern = concern;
        this.capacity = capacity;
    }

    public Group bindToEntity() {
        return new Group(
                this.name,
                this.introduce,
                new GroupLocation(this.locationDo, this.locationSi),
                GroupConcern.valueOf(this.concern), // TODO : 코드 변경 필요. 성능
                this.capacity
        );
    }
}
