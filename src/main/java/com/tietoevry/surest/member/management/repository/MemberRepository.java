package com.tietoevry.surest.member.management.repository;


import com.tietoevry.surest.member.management.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;
import java.util.UUID;


public interface MemberRepository extends JpaRepository<Member, UUID> {
    Page<Member> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(
            String firstName, String lastName, Pageable pageable);

    Optional<Member> findByEmail(String email);
}

