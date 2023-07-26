package com.mutsa.market.exception;

public class UserNotUniqueException extends Status500Exception{
    public UserNotUniqueException(String message) {
        super(message);
    }
}
