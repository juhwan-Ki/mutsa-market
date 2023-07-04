package com.mutsa.market.exception;

public class PasswordException extends Status400Exception{
    public PasswordException() {
        super("작성자 혹은 비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
    }
}
