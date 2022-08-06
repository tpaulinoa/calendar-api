package code.thiago.app.exceptions.handler;

import code.thiago.app.exceptions.InvalidDateTimeException;
import code.thiago.app.exceptions.PersonNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {

    @ExceptionHandler(value = PersonNotFoundException.class)
    protected ResponseEntity<String> handleEntityNotFoundException(PersonNotFoundException ex, WebRequest request) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = DateTimeParseException.class)
    protected ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponseJsonBody("Invalid input format! A slot time must be in the hh:mm AM/PM " +
                        "format and date in the YYYY-MM-DD format."));
    }

    @ExceptionHandler(value = InvalidDateTimeException.class)
    protected ResponseEntity<String> handleInvalidDateTimeException(InvalidDateTimeException ex, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponseJsonBody(ex.getMessage()));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponseJsonBody("Some constraint violation, probably you're trying to create a duplicated slot..."));
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<String> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildErrorResponseJsonBody(ex.getMessage()));
    }

    private String buildErrorResponseJsonBody(String message) {
        return "{ \"error\": \"" + message + "\" }";
    }
}
