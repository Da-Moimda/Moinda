package com.social.moinda.core.domains.meeting.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MeetingLocation {

    private String shopName;

    private String streetName;

    public MeetingLocation(String shopName, String streetName) {
        this.shopName = shopName;
        this.streetName = streetName;
    }
}
