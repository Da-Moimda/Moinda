package com.social.moinda.api.group.repository;

import com.social.moinda.core.domains.group.entity.Group;
import com.social.moinda.core.domains.group.entity.GroupConcern;
import com.social.moinda.core.domains.group.entity.GroupLocation;
import com.social.moinda.core.domains.group.entity.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@Commit
public class GroupRepositoryTests {

    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    public void insertTest() {
        groupRepository.saveAndFlush(
                new Group(
                        "사용자1",
                        "안녕하세요",
                        new GroupLocation("경기도", "시흥히"),
                        GroupConcern.STUDY,
                        30
                )
        );
        groupRepository.saveAndFlush(
                new Group(
                        "사용자122",
                        "안녕하세요",
                        new GroupLocation("경기도", "시흥히"),
                        GroupConcern.STUDY,
                        30
                )
        );
    }

    @DisplayName("조회 성공 테스트")
    @Test
    public void 조회_성공_테스트() {

        List<Group> all = groupRepository.findAll();
        all.forEach(System.out::println);

    }

    @DisplayName("경기도로 조회하기.")
    @Test
    public void 경기도로_조회하기() {
        List<Group> all = groupRepository.findAll();
        all.forEach(System.out::println);
        List<Group> 경기도 = groupRepository.findGroupsByLocationSi("시흥히");
        System.out.println("경기도 = " + 경기도);
    }

     @TestConfiguration
     @EnableJpaAuditing
     static class Config{

     }
}
