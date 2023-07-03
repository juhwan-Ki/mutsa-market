package com.mutsa.market.exception;

public class ProposalNotFoundException extends Status404Exception{
    public ProposalNotFoundException() {
        super("해당 제안은 존재하지 않습니다.");
    }
}
