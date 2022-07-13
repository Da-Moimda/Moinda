package com.social.moinda.core.domains.member.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Query("select m.email from Member m where m.email = :email")
    boolean existByEmail(String email);

    boolean deleteByEmail(String email);
}
