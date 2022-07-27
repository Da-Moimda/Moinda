package com.social.moinda.core.domains.group.dto;

import com.social.moinda.core.domains.group.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

public class Groups {
    private final List<Group> groups;

    public Groups(List<Group> groups) {
        this.groups = groups;
    }

    public List<GroupDto> getGroupDtoList() {
        return this.groups.stream()
                .map(Group::bindToGroupDto)
                .collect(Collectors.toList());
    }
}
