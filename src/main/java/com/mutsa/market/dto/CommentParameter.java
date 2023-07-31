package com.mutsa.market.dto;

import lombok.Data;

@Data
public class CommentParameter {
    private String writer;
    private String content;
    private String reply;
}
