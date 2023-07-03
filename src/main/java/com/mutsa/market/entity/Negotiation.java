package com.mutsa.market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Negotiation extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "suggested_price")
    private Integer suggestedPrice;

}
