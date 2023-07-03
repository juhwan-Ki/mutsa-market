package com.mutsa.market.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {

    private String status;
    private String writer;
    private String password;
}
