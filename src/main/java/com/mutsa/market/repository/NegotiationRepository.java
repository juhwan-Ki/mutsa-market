package com.mutsa.market.repository;

import com.mutsa.market.entity.Negotiation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegotiationRepository extends JpaRepository<Negotiation, Long>{
}
