package com.mutsa.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mutsa.market.entity.Negotiation;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NegotiationDTO {

    private Long id;
    private Long itemId;
    private Integer suggestedPrice;
    private String status;
    private String writer;
    private String password;

    public static NegotiationDTO fromEntity(Negotiation negotiation){
        NegotiationDTO negotiationDTO = new NegotiationDTO();
        negotiationDTO.setId(negotiation.getId());
        negotiationDTO.setSuggestedPrice(negotiation.getSuggestedPrice());
        negotiationDTO.setStatus(negotiation.getStatus());

        return negotiationDTO;
    }
}
