package com.business.finder;

import com.business.finder.investment.application.exceptions.InvestmentProposalNotFoundException;
import com.business.finder.investment.application.exceptions.NoAccessToInvestmentProposalException;
import com.business.finder.partnership.application.exception.NoAccessToPartnershipProposalException;
import com.business.finder.partnership.application.exception.PartnershipProposalIsNotFoundException;
import com.business.finder.user.application.exception.BfUserException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.*;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;

@ControllerAdvice
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

    @ExceptionHandler({IllegalArgumentException.class,
            BfUserException.class, ConversionFailedException.class})
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
