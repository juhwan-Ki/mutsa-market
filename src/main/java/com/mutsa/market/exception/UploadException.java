package com.mutsa.market.exception;

public class UploadException extends Status500Exception {
    public UploadException() {
        super("파일 업로드에 실패하였습니다.");
    }
}
