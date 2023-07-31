package com.mutsa.market.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "salse_item")
@Getter
@Setter
public class SalesItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String title;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "min_price_wanted")
    private Integer minPriceWanted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "salesItem")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "salesItem")
    private List<Negotiation> negotiations = new ArrayList<>();
}
