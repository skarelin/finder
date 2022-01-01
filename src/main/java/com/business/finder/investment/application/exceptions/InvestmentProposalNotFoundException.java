package com.business.finder.investment.application.exceptions;

public class InvestmentProposalNotFoundException extends RuntimeException{

    public InvestmentProposalNotFoundException(String message) {
        super(message);
    }
}
