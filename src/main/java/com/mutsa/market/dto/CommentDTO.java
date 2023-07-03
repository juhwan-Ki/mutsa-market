package com.mutsa.market.dto;

import com.mutsa.market.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    @NotNull
    private Long itemId;
    @NotNull
    @NotEmpty
    private String writer;
    @NotNull
    @NotEmpty
    private String content;
    @NotNull
    @NotEmpty
    private String password;
    private String reply;

    public static CommentDTO fromEntity(Comment comment){
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setItemId(comment.getItemId());
        commentDTO.setWriter(comment.getWriter());
        commentDTO.setPassword(comment.getPassword());
        commentDTO.setContent(comment.getContent());
        commentDTO.setReply(comment.getReply());
        return commentDTO;
    }
}
