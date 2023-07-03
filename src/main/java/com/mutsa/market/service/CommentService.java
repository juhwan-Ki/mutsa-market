package com.mutsa.market.service;

import com.mutsa.market.dto.CommentDTO;
import com.mutsa.market.dto.CommentParameter;
import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.entity.Comment;
import com.mutsa.market.exception.CommentNotFoundException;
import com.mutsa.market.exception.ItemNotFoundException;
import com.mutsa.market.exception.PasswordException;
import com.mutsa.market.repository.CommentRepository;
import com.mutsa.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final SalesItemRepository salesItemRepository;

    // 상품 댓글 추가
    public ResponseDTO createComment(Long itemId, CommentDTO commentDTO) {
        // 댓글을 추가할 상품이 없으면 에러 발생
        if(!salesItemRepository.existsById(itemId)) {
            throw new ItemNotFoundException();
        }
        Comment entity = new Comment();
        entity.setItemId(commentDTO.getItemId());
        entity.setWriter(commentDTO.getWriter());
        entity.setPassword(commentDTO.getPassword());
        entity.setContent(commentDTO.getContent());
        commentRepository.save(entity);

        ResponseDTO response = new ResponseDTO();
        response.setMessage("댓글이 등록되었습니다.");
        return response;
    }

    // 댓글 조회 (페이지 네이션)
    public Page<CommentDTO> readAllCommentByItem(Long itemId, Integer page, Integer limit) {// 댓글을 추가할 상품이 없으면 에러 발생
        // 댓글을 추가할 상품이 없으면 에러 발생
        if(!salesItemRepository.existsById(itemId)) {
            throw new ItemNotFoundException();
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by("id"));
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(CommentDTO::fromEntity);
    }

    // 댓글 수정 및 답변 추가
    public ResponseDTO updateComment(Long itemId, Long commentId, CommentParameter parameter) {
        // 댓글을 추가할 상품이 없으면 에러 발생
        if(!salesItemRepository.existsById(itemId)) {
            throw new ItemNotFoundException();
        }
        Comment updateComment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        ResponseDTO response = new ResponseDTO();

        // 비밀번호가 맞으면 수정
        if(updateComment.getPassword().equals(parameter.getPassword())){
            // content가 비어있지 않으면 댓글 수정
            if(parameter.getContent() != null && !parameter.getContent().isEmpty()){
                updateComment.setContent(parameter.getContent());
                response.setMessage("댓글이 수정되었습니다.");
            } // reply가 비어있지 않으면 댓글 답변을 추가
            else if(parameter.getReply() != null && !parameter.getReply().isEmpty()){
                updateComment.setReply(parameter.getReply());
                response.setMessage("댓글에 답변이 추가되었습니다.");
            }
            commentRepository.save(updateComment);
        } else {
            throw new PasswordException();
        }
        return response;
    }

    // 댓글 삭제
    public ResponseDTO deleteComment(Long itemId, Long commentId, CommentParameter parameter) {
        // 댓글을 추가할 상품이 없으면 에러 발생
        if(!salesItemRepository.existsById(itemId)) {
            throw new ItemNotFoundException();
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        // 비밀번호가 맞으면 삭제
        if(comment.getPassword().equals(parameter.getPassword())){
            commentRepository.delete(comment);
        } else {
            throw new PasswordException();
        }

        ResponseDTO response = new ResponseDTO();
        response.setMessage("댓글을 삭제했습니다.");
        return response;
    }
}
