package com.social.moinda.api.group.dto;


import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupConcern;
import com.social.moinda.core.domains.group.entity.GroupLocation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupCreateDto {
    @NotNull(message = "사용자 번호가 필요합니다.")
    private Long memberId;


    @NotEmpty(message = "그룹명을 입력해주세요.")
    @Size(min = 1)
    private String name;

    @NotEmpty(message = "소개글을 입력해주세요.")
    private String introduce;

    // TODO : 네이밍 변경필요
    @NotBlank(message = "도시를 입력하세요.")
    private String locationDo;

    @NotBlank(message = "상세 주소를 입력해주세요.")
    private String locationSi;

    @NotBlank(message = "그룹 주제를 선택해주세요.")
    private String concern;

    @Min(value = 10, message = "최소 10명부터 가능합니다.")
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
