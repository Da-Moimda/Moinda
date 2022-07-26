package com.social.moinda.api.group.service;

import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.group.entity.GroupQueryRepository;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupQueryServiceTests {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupQueryRepository groupQueryRepository;

    @InjectMocks
    private GroupQueryService groupQueryService;

    @DisplayName("전체 그룹 조회 - 성공")
    @Test
    void findAllSuccessTest() {

        List<GroupDto> dtoList = List.of(
                new GroupDto(1L, "그룹1", "부천시", "FREE", 20),
                new GroupDto(2L, "그룹2", "시흥시", "STUDY", 30),
                new GroupDto(3L, "그룹3", "마포구", "FREE", 11)
        );

        given(groupQueryRepository.findGroupAll()).willReturn(dtoList);

        List<GroupDto> resultList = groupQueryService.searchGroups();

        then(groupQueryRepository).should(times(1)).findGroupAll();
        assertThat(resultList.size()).isEqualTo(dtoList.size());
        assertThatNoException();
    }

    @DisplayName("특정 그룹명 검색 - 성공")
    @Test
    void searchGroupSuccessTest() {

        List<GroupDto> dtoList = List.of(
                new GroupDto(1L, "그룹1", "부천시", "FREE", 20),
                new GroupDto(2L, "그룹2", "시흥시", "STUDY", 30),
                new GroupDto(3L, "그룹3", "마포구", "FREE", 11)
        );

        given(groupQueryRepository.findAllByNameContains(anyString())).willReturn(dtoList);

        groupQueryService.searchGroups(anyString());

        then(groupQueryRepository).should(times(1)).findAllByNameContains(anyString());

    }
}
