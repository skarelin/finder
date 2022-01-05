package com.business.finder;

import com.business.finder.investment.application.exceptions.InvestmentProposalNotFoundException;
import com.business.finder.investment.application.exceptions.NoAccessToInvestmentProposalException;
import com.business.finder.partnership.application.exception.NoAccessToPartnershipProposalException;
import com.business.finder.partnership.application.exception.PartnershipProposalIsNotFoundException;
import com.business.finder.upload.application.exception.UploadPictureException;
import com.business.finder.user.application.exception.BfUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
// TODO. Add trace-id here I think. For every RuntimeException. Easier to analyze later.
    // TODO. Add also handle for Exception. Maybe?
class CustomGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
        List<String> errors = ex
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(x -> x.getField() + " - " + x.getDefaultMessage())
            .collect(Collectors.toList());
        return handleError(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return handleError(HttpStatus.EXPECTATION_FAILED, List.of("Unable to upload. File is too large! Internal info: " + exc));
    }

    @ExceptionHandler({IllegalArgumentException.class,
            BfUserException.class,
            UploadPictureException.class})
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex) {
        return handleError(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler({PartnershipProposalIsNotFoundException.class, InvestmentProposalNotFoundException.class})
    public ResponseEntity<Object> handleNoContent(RuntimeException ex) {
        return handleError(HttpStatus.NO_CONTENT, List.of(ex.getMessage()));
    }

    @ExceptionHandler({NoAccessToPartnershipProposalException.class, NoAccessToInvestmentProposalException.class})
    public ResponseEntity<Object> handleForbidden(RuntimeException ex) {
        return handleError(HttpStatus.FORBIDDEN, List.of(ex.getMessage()));
    }

    private ResponseEntity<Object> handleError(HttpStatus status, List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", errors);
        return new ResponseEntity<>(body, status);
    }
}
