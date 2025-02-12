package com.dareuda.givetree.donation.infrastructure;

import com.dareuda.givetree.donation.domain.FoundationDonateSubscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface FoundationDonateSubscriptionRepository extends Repository<FoundationDonateSubscription, Long> {
    FoundationDonateSubscription save(FoundationDonateSubscription foundationDonateSubscription);

    boolean existsByMemberIdAndFoundationId(long memberId, long foundationId);

    @Query("""
           SELECT fds
           FROM FoundationDonateSubscription fds
           JOIN FETCH fds.foundation
           JOIN FETCH fds.foundation.member
           LEFT JOIN FETCH fds.foundation.member.profileImage
           WHERE fds.member.id=:memberId
           """)
    Slice<FoundationDonateSubscription> findByMemberIdFetchFoundation(long memberId, Pageable pageable);

    Optional<FoundationDonateSubscription> findByMemberIdAndFoundationId(long userId, long foundationId);

    long countByMemberId(long memberId);

    void delete(FoundationDonateSubscription foundationDonateSubscription);

    long countByFoundationId(long foundationId);
}
