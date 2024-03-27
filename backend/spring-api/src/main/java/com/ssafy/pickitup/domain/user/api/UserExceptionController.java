package com.ssafy.pickitup.domain.user.api;

import static com.ssafy.pickitup.global.api.ApiUtils.error;

import com.ssafy.pickitup.global.api.ApiUtils;
import com.ssafy.pickitup.domain.user.exception.DuplicateUsernameException;
import com.ssafy.pickitup.domain.user.exception.ErrorMessageDto;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.security.exception.AuthNotFoundException;
import com.ssafy.pickitup.security.exception.PasswordException;
import java.sql.SQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionController {

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(Throwable throwable,
        HttpStatus status) {
        return newResponse(throwable.getMessage(), status);
    }

    private ResponseEntity<ApiUtils.ApiResult<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(error(message, status), headers, status);
    }

    @ExceptionHandler({
        UserNotFoundException.class
    })
    public ResponseEntity<?> handleUserNotFoundException(Exception exception) {
        return newResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        AuthNotFoundException.class,
        PasswordException.class,
        DuplicateUsernameException.class
    })
    public ResponseEntity<?> handleAuthException(Exception exception) {
        return newResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageDto> handleHttpMessageNotReadableException() {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto("잘못된 JSON 요청입니다");
        return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorMessageDto> handleSQLException() {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto("잘못된 SQL 요청입니다");
        return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
