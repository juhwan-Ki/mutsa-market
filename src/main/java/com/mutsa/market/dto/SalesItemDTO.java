package com.mutsa.market.dto;

import com.mutsa.market.entity.SalesItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SalesItemDTO {

    private Long id;
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @NotEmpty
    private Integer minPriceWanted;
    private String writer;
    private String password;
    private String imageUrl;
    private String status;

    public static SalesItemDTO fromEntity(SalesItem salesItem) {
        SalesItemDTO itemDto = new SalesItemDTO();

        itemDto.setId(salesItem.getId());
        itemDto.setTitle(salesItem.getTitle());
        itemDto.setDescription(salesItem.getDescription());
        itemDto.setImageUrl(salesItem.getImageUrl());
        itemDto.setMinPriceWanted(salesItem.getMinPriceWanted());
        itemDto.setWriter(salesItem.getWriter());
        itemDto.setPassword(salesItem.getPassword());
        itemDto.setStatus(salesItem.getStatus());

        return itemDto;
    }

    public static SalesItemDTO fromEntityOne(SalesItem salesItem) {
        SalesItemDTO itemDto = new SalesItemDTO();

        itemDto.setTitle(salesItem.getTitle());
        itemDto.setDescription(salesItem.getDescription());
        itemDto.setMinPriceWanted(salesItem.getMinPriceWanted());
        itemDto.setStatus(salesItem.getStatus());

        return itemDto;
    }
}
