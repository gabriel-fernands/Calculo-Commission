package dev.gabrielfernandes.comission_api.commission.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalComissionExceptionHandler {

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ExceptionResponseComission> handleNotFound(ChangeSetPersister.NotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponseComission> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(VendaInvalidaException.class)
    public ResponseEntity<ExceptionResponseComission> handleBadRequest(VendaInvalidaException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(RegraInvalidaException.class)
    public ResponseEntity<ExceptionResponseComission> handleNegocio(RegraInvalidaException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.UNPROCESSABLE_CONTENT, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseComission> handleAll(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ExceptionResponseComission> buildErrorResponse(
            Exception ex,
            HttpStatus status,
            HttpServletRequest request) {

        ExceptionResponseComission error = new ExceptionResponseComission(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(error);
    }
}

