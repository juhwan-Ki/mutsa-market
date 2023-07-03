package com.mutsa.market.repository;

import com.mutsa.market.entity.Negotiation;
import com.mutsa.market.entity.SalesItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NegotiationRepository extends JpaRepository<Negotiation, Long>{
    Page<Negotiation> findAllByItemId(Long itemId, Pageable pageable);

    Page<Negotiation> findAllByItemIdAndWriterAndPassword(Long itemId, String writer, String password, Pageable pageable);

    List<Negotiation> findAllByItemIdAndIdNot(Long itemId, Long proposalId);
}
