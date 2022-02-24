package todolist;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex, WebRequest webRequest){
        logger.error("Error", ex);
        return handleExceptionInternal(ex
                , new ExceptionResponseBody(ex.getClass().getSimpleName(), ex.getMessage())
                , new HttpHeaders()
                , HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
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
}


@Data
class ExceptionResponseBody {
    public String error;
    public String description;

    ExceptionResponseBody(String error, String text){
        this.error = error;
        this.description = text;
    }
}