package com.mutsa.market.controller;

import com.mutsa.market.dto.SalesItemParameter;
import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.dto.SalesItemDTO;
import com.mutsa.market.service.SalesItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class SalesItemController {
    private final SalesItemService service;

    // 상품 등록
    @PostMapping()
    public ResponseDTO createItem(@RequestBody SalesItemDTO salesItemDTO){
       return service.createItem(salesItemDTO);
    }

    // 상품 조회(페이지네이션)
    @GetMapping()
    public Page<SalesItemDTO> readAllItems(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer limit
    ){
        return service.readAllItems(page, limit);
    }

    // 상품 단건조회
    @GetMapping("/{itemId}")
    public SalesItemDTO readItem(@PathVariable Long itemId){
        return service.readItem(itemId);

    }

    // 상품 수정
    @PutMapping("/{itemId}")
    public ResponseDTO updateItem(@PathVariable Long itemId, @RequestBody SalesItemDTO salesItemDTO){
        return service.updateItem(itemId, salesItemDTO);
    }

    // 상품 이미지 등록
    @PutMapping(value = "/{itemId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO uploadItemImage(
            @PathVariable Long itemId,
            @RequestParam MultipartFile image,
            @RequestParam String writer,
            @RequestParam String password
    ){
        return service.uploadItemImage(itemId, image, writer, password);
    }

    // 상품 삭제
    @DeleteMapping("/{itemId}")
    public ResponseDTO deleteItem(
            @PathVariable Long itemId,
            @RequestBody SalesItemParameter salesItemParameter
            ){
        return service.deleteItem(itemId, salesItemParameter);
    }
}
