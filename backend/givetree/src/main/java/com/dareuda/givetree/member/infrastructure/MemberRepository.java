package com.dareuda.givetree.member.infrastructure;

import com.dareuda.givetree.member.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    @Query("SELECT m FROM Member m left join fetch m.profileImage WHERE m.id = :id and m.isDeleted = false")
    Optional<Member> findById(long id);

    Optional<Member> findByEmail(String email);

    Optional<Member> getReferenceById(long id);

    boolean existsByEmail(String email);
}
