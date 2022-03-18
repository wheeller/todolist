package todolist.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    // all repository exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest webRequest){
        logger.error("Error", ex);
        return handleExceptionInternal(ex
                , new ExceptionResponseBody(ex.getClass().getSimpleName(), ex.getMessage())
                , new HttpHeaders()
                , HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    // DataIntegrityViolationException extends NonTransientDataAccessException
    // org.springframework.dao.DataIntegrityViolationException:
    // not-null property references a null or transient value :
    // todolist.TodoItem.user; nested exception is org.hibernate.PropertyValueException:
    // not-null property references a null or transient value : todolist.TodoItem.user
    @ExceptionHandler(NonTransientDataAccessException.class)
    public ResponseEntity<Object> handleNonTransientDataAccessException(NonTransientDataAccessException ex, WebRequest webRequest){
        logger.error(ex.getMessage());
        return handleExceptionInternal(ex
                , new ExceptionResponseBody("BAD_REQUEST", ex.getMessage())
                , new HttpHeaders()
                , HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest webRequest){
        return handleExceptionInternal(ex
                , new ExceptionResponseBody("ITEM_NOT_FOUND", ex.getMessage())
                , new HttpHeaders()
                , HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest){
        return handleExceptionInternal(ex
                , new ExceptionResponseBody("ILLEGAL_VALUE", ex.getMessage())
                , new HttpHeaders()
                , HttpStatus.BAD_REQUEST, webRequest);
    }

    // JWT ExpiredJwtException
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest webRequest) {
        return handleExceptionInternal(ex
                , new ExceptionResponseBody("JWT_EXPIRED", ex.getMessage())
                , new HttpHeaders()
                , HttpStatus.NOT_FOUND, webRequest);
    }
}


