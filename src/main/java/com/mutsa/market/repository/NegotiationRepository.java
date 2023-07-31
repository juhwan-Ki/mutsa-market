package com.mutsa.market.repository;

import com.mutsa.market.entity.Negotiation;
import com.mutsa.market.entity.SalesItem;
import com.mutsa.market.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NegotiationRepository extends JpaRepository<Negotiation, Long>{
    Page<Negotiation> findAllBySalesItem(SalesItem item, Pageable pageable);

    Page<Negotiation> findAllBySalesItemAndUser(SalesItem item, User user, Pageable pageable);

    List<Negotiation> findAllBySalesItemAndIdNot(SalesItem item, Long proposalId);
}
