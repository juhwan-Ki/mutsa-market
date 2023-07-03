package com.mutsa.market.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "salse_item")
@Data
public class SalesItem extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "min_price_wanted")
    private Integer minPriceWanted;
}
