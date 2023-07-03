package com.mutsa.market.exception;

public class ItemNotFoundException extends Status404Exception{
    public ItemNotFoundException() {
        super("해당 물품은 존재하지 않습니다.");
    }
}
