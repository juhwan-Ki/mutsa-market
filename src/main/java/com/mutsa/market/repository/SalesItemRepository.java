package com.mutsa.market.repository;

import com.mutsa.market.entity.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesItemRepository extends JpaRepository<SalesItem, Long> {
}
