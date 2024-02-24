package com.younes.reskilingproject.bookManagement.restController;

import com.younes.reskilingproject.bookManagement.errorHandler.AuthorException;
import com.younes.reskilingproject.bookManagement.errorHandler.BookException;
import com.younes.reskilingproject.bookManagement.errorHandler.CategoryException;
import com.younes.reskilingproject.bookManagement.errorHandler.models.AuthorErrorResponse;
import com.younes.reskilingproject.bookManagement.errorHandler.models.BookErrorResponse;
import com.younes.reskilingproject.bookManagement.errorHandler.models.CategoryErrorResponse;
import com.younes.reskillingproject.userManagement.security.error.EmailExistsException;
import com.younes.reskillingproject.userManagement.security.error.UsernameExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    //  Exception Internal Server Error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BookErrorResponse> handleOtherException(Exception exc) {
        BookErrorResponse customErr = new BookErrorResponse();

        customErr.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        customErr.setMessage(exc.getMessage());
        customErr.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErr,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //  Exception Book Not Found
    @ExceptionHandler(BookException.class)
    public ResponseEntity<BookErrorResponse> handleException(BookException exc) {
        BookErrorResponse customErr = new BookErrorResponse();

        customErr.setStatus(HttpStatus.NOT_FOUND);
        customErr.setMessage(exc.getMessage());
        customErr.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(customErr, HttpStatus.NOT_FOUND);
    }
    //  Exception Author Not Found
    @ExceptionHandler(AuthorException.class)
    public ResponseEntity<AuthorErrorResponse> handlleException(AuthorException exc) {
        AuthorErrorResponse customErr = new AuthorErrorResponse();

        customErr.setStatus(HttpStatus.NOT_FOUND);
        customErr.setMessage(exc.getMessage());
        customErr.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(customErr, HttpStatus.NOT_FOUND);
    }
    //  Exception Category Not Found
    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<CategoryErrorResponse> handlleException(CategoryException exc) {
        CategoryErrorResponse customErr = new CategoryErrorResponse();

        customErr.setStatus(HttpStatus.NOT_FOUND);
        customErr.setMessage(exc.getMessage());
        customErr.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(customErr, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<String> handleUsernameExistsException(UsernameExistsException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<String> handleEmailExistsException(EmailExistsException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthException(AuthenticationException exc) {
        return new ResponseEntity<>("Authentication Failed, " + exc.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
