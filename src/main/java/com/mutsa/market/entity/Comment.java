package com.mutsa.market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "item_id")
    private Long itemId;
    private String writer;
    private String password;
    private String content;
    private String reply;
}
