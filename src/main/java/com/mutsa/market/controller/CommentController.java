package com.mutsa.market.controller;

import com.mutsa.market.dto.CommentDTO;
import com.mutsa.market.dto.CommentParameter;
import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items/{itemId}/comments")
public class CommentController {
    private final CommentService service;

    // 상품 댓글 추가
    @PostMapping()
    public ResponseDTO createComment(@PathVariable Long itemId, @RequestBody CommentDTO commentDTO){
        return service.createComment(itemId, commentDTO);
    }

    // 댓글 조회 (페이지네이션)
    @GetMapping()
    public Page<CommentDTO> readAllCommentByItem(
            @PathVariable Long itemId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "0") Integer limit
    ){
        return service.readAllCommentByItem(itemId, page, limit);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseDTO updateComment(
            @PathVariable Long itemId,
            @PathVariable Long commentId,
            @RequestBody CommentParameter parameter
    ) {
        return service.updateComment(itemId, commentId, parameter);
    }

    // 댓글 답변 추가
    @PutMapping("{commentId}/reply")
    public ResponseDTO updateCommentReply(
            @PathVariable Long itemId,
            @PathVariable Long commentId,
            @RequestBody CommentParameter parameter
    ) {
        return service.updateComment(itemId, commentId, parameter);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseDTO deleteComment(
            @PathVariable Long itemId,
            @PathVariable Long commentId,
            @RequestBody CommentParameter parameter
    ){
        return service.deleteComment(itemId, commentId, parameter);
    }
}
