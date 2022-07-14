package com.social.moinda.core.domains.group.entity;

import com.social.moinda.core.domains.group.dto.GroupDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    /*
        TODO : convert Querydsl
     */
    @Query("select g.id, g.name, g.concern, g.location, count(m) as userNum from Group g join Member m where g.id = m.id")
    public List<GroupDto> findGroupAll();

    // Mysql 이기 때문에 like concat
    @Query("select g.id, g.name, g.concern, g.location, count(m) as userNum from Group g join Member m where g.id = m.id AND g.name like concat('%', :search, '%') ")
    public List<GroupDto> findAllByNameContains(String search);

}
