package com.social.moinda.api.group.service;

import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupQueryService {

    /*
        TODO : to Querydsl
     */
    private final GroupRepository groupRepository;

    public List<GroupDto> searchGroups() {
        List<GroupDto> dtoList = groupRepository.findGroupAll();
        return dtoList;
    }

    public List<GroupDto> searchGroups(String search) {
        List<GroupDto> dtoList = groupRepository.findAllByNameContains(search);
        return dtoList;
    }
}
