package com.social.moinda.core.domains.group.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByName(String name);

    /*
        값 타입 기준으로 조회하기
         == List<Group> findGroupsByLocation_LocationSi(String locationSi);
     */
    @Query("select g from Group g where g.location.locationSi = :si")
    List<Group> findGroupsByLocationSi(@Param("si") String locationSi);


}
