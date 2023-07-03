package com.mutsa.market.exception;

public class CommentNotFoundException extends Status404Exception{
    public CommentNotFoundException() {
        super("해당 댓글은 존재하지 않습니다.");
    }
}
