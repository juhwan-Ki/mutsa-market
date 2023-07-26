package com.mutsa.market.exception;

public class PasswordException extends Status400Exception{
    public PasswordException(String message) {
        super(message);
    }
}
