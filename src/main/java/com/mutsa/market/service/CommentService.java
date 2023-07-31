package com.mutsa.market.service;

import com.mutsa.market.dto.CommentDTO;
import com.mutsa.market.dto.CommentParameter;
import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.entity.Comment;
import com.mutsa.market.entity.SalesItem;
import com.mutsa.market.entity.User;
import com.mutsa.market.exception.CommentNotFoundException;
import com.mutsa.market.exception.ItemNotFoundException;
import com.mutsa.market.exception.PasswordException;
import com.mutsa.market.jwt.JwtValidationCheck;
import com.mutsa.market.repository.CommentRepository;
import com.mutsa.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final SalesItemRepository salesItemRepository;
    private final JwtValidationCheck validationCheck;

    // 상품 댓글 추가
    @Transactional
    public ResponseDTO createComment(Long itemId, CommentDTO commentDTO) {
        // 댓글을 추가할 상품이 없으면 에러 발생
        Optional<SalesItem> findItem = salesItemRepository.findById(itemId);
        if(findItem.isEmpty()) {
            throw new ItemNotFoundException();
        }
        User user = validationCheck.userValidationCheck();

        Comment entity = new Comment();
        entity.setSalesItem(findItem.get());
        entity.setWriter(commentDTO.getWriter());
        entity.setPassword(commentDTO.getPassword());
        entity.setContent(commentDTO.getContent());
        entity.setUser(user);
        commentRepository.save(entity);

        ResponseDTO response = new ResponseDTO();
        response.setMessage("댓글이 등록되었습니다.");
        return response;
    }

    // 댓글 조회 (페이지 네이션)
    public Page<CommentDTO> readAllCommentByItem(Long itemId, Integer page, Integer limit) {
        // 댓글을 추가할 상품이 없으면 에러 발생
        if(!checkValidation(itemId)){
            throw new ItemNotFoundException();
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by("id"));
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(CommentDTO::fromEntity);
    }

    // 댓글 수정
    @Transactional
    public ResponseDTO updateComment(Long itemId, Long commentId, CommentParameter parameter) {
        // 댓글을 추가할 상품이 없으면 에러 발생
        if(!checkValidation(itemId)) {
            throw new ItemNotFoundException();
        }
        Comment updateComment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        User user = validationCheck.userValidationCheck();
        // 작성자와 비밀번호가 맞으면 수정
        if(updateComment.getUser().equals(user)){
            updateComment.setContent(parameter.getContent());
            commentRepository.save(updateComment);
        } else {
            throw new PasswordException("작성자가 일치하지 않습니다. 다시 확인해주세요.");
        }

        ResponseDTO response = new ResponseDTO();
        response.setMessage("댓글이 수정되었습니다.");
        return response;
    }

    // 답변 추가
    @Transactional
    public ResponseDTO createCommentReply(Long itemId, Long commentId, CommentParameter parameter) {
        // 댓글을 추가할 상품이 없으면 에러 발생
        if(!checkValidation(itemId)) {
            throw new ItemNotFoundException();
        }
        SalesItem findItem = salesItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        Comment updateComment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        User user = validationCheck.userValidationCheck();

        // 작성자와 비밀번호가 맞으면 답글 추가
        if(updateComment.getUser().equals(user)){
            updateComment.setReply(parameter.getReply());
            commentRepository.save(updateComment);
        } else {
            throw new PasswordException("작성자가 일치하지 않습니다. 다시 확인해주세요.");
        }

        ResponseDTO response = new ResponseDTO();
        response.setMessage("댓글에 답변이 추가되었습니다.");

        return response;
    }
    // 댓글 삭제
    @Transactional
    public ResponseDTO deleteComment(Long itemId, Long commentId, CommentParameter parameter) {
        // 댓글을 추가할 상품이 없으면 에러 발생
        if(!checkValidation(itemId)) {
            throw new ItemNotFoundException();
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        User user = validationCheck.userValidationCheck();
        // 작성자와 비밀번호가 맞으면 삭제
        if(comment.getUser().equals(user)){
            commentRepository.delete(comment);
        } else {
            throw new PasswordException("작성자가 일치하지 않습니다. 다시 확인해주세요.");
        }

        ResponseDTO response = new ResponseDTO();
        response.setMessage("댓글을 삭제했습니다.");
        return response;
    }

    // 해당 상품이 있는지 확인
    @Transactional
    public boolean checkValidation(Long itemId) {
        return salesItemRepository.existsById(itemId);
    }
}
