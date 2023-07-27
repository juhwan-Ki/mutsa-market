package com.mutsa.market.controller;

import com.mutsa.market.dto.NegotiationDTO;
import com.mutsa.market.dto.NegotiationParameter;
import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.service.NegotiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items/{itemId}/proposals")
public class NegotiationController {
    private final NegotiationService service;

    // 구매 제안 등록
    @PostMapping()
    public ResponseDTO createProposal(@PathVariable Long itemId, @RequestBody NegotiationParameter parameter){
        return service.createProposal(itemId, parameter);
    }

    // 구매 제안 조회
    @GetMapping()
    public Page<NegotiationDTO> readAllProposal(
            @PathVariable Long itemId,
            @RequestParam String writer,
            @RequestParam String password,
            @RequestParam(defaultValue = "0") Integer page
    ){
        return service.readAllProposal(itemId, writer, password, page);
    }

    // 구매 제안 수정 및 상태 변경
    @PutMapping("/{proposalId}")
    public ResponseDTO updateProposal(
            @PathVariable Long itemId,
            @PathVariable Long proposalId,
            @RequestBody NegotiationParameter parameter
    ){
        return service.updateProposal(itemId, proposalId, parameter);
    }

    // 구매 제안 삭제
    @DeleteMapping("/{proposalId}")
    public ResponseDTO deleteProposal(
              @PathVariable Long itemId,
              @PathVariable Long proposalId,
              @RequestBody NegotiationParameter parameter
    ){
        return service.deleteProposal(itemId, proposalId, parameter);
    }
}
