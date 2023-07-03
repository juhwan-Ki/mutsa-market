package com.mutsa.market.exception;

public abstract class Status500Exception extends RuntimeException {
    public Status500Exception(String message) {
        super(message);
    }
}
