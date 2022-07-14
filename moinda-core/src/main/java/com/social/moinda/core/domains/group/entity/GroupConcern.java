package com.social.moinda.core.domains.group.entity;

import lombok.AllArgsConstructor;

import java.util.Arrays;

public enum GroupConcern {
    FREE("free"),
    STUDY("study");

    private String concern;

    GroupConcern(String concern) {
        this.concern = concern;
    }
}
