package com.mutsa.market.exception;

public class ItemStatusException extends Status500Exception{
    public ItemStatusException() {
        super("해당 상품은 이미 판매되었습니다.");
    }
}
