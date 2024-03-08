package com.ssafy.pickitup.domain.user.api;

import com.ssafy.pickitup.domain.user.exception.ErrorMessageDto;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleUserNotFoundException(
        UserNotFoundException exception) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(exception.getMessage());
        return new ResponseEntity<>(errorMessageDto, HttpStatus.NOT_FOUND);
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
