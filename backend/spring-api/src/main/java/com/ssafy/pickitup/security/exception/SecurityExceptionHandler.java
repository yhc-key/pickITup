package com.ssafy.pickitup.security.exception;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(RefreshTokenException.class) // RT 관련 예외 처리
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(
        RefreshTokenException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ErrorResponse("ERROR_40101", exception.getMessage()));
    }

    @ExceptionHandler(JwtBlackListException.class) // JWT Black List(로그아웃된 엑세스 토큰 관리) 예외 처리
    public ResponseEntity<ErrorResponse> handleJwtBlackListException(
        MalformedJwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ErrorResponse("ERROR_40103", exception.getMessage()));
    }

    @ExceptionHandler(JwtException.class) // JWT 유효성 검사
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ErrorResponse("ERROR_40104", exception.getMessage()));
    }

//    @ExceptionHandler({AuthNotFoundException.class, PasswordException.class}) // 로그인 예외 처리
//    public ResponseEntity<ErrorResponse> handleLoginException(Exception exception) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//            new ErrorResponse("ERROR_40001", exception.getMessage()));
//    }

    @ExceptionHandler(UnsupportedJwtException.class) // 지원하지 않는 토큰 인증 방식 예외 처리
    public ResponseEntity<ErrorResponse> handleJwtTokenException(UnsupportedJwtException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ErrorResponse("ERROR_40002", exception.getMessage()));
    }

}
