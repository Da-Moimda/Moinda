package com.social.talk.core.domains.member.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    boolean existByEmail(String email);

    boolean deleteByEmail(String email);
}
