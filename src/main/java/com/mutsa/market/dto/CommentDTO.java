package com.mutsa.market.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mutsa.market.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
        commentDTO.setContent(comment.getContent());
        commentDTO.setReply(comment.getReply());
        return commentDTO;
    }

}
