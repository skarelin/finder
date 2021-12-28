package com.business.finder.partnership.application.exception;

public class NoAccessToPartnershipProposalException extends RuntimeException {

    public NoAccessToPartnershipProposalException(String errorMessage) {
        super(errorMessage);
    }

}
