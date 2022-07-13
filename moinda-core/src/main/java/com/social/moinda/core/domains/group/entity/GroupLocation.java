package com.social.moinda.core.domains.group.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupLocation {

    // TODO : 네이밍 변경필요
    private String locationDo;
    private String locationSi;

    public GroupLocation(String locationDo, String locationSi) {
        this.locationDo = locationDo;
        this.locationSi = locationSi;
    }
}
