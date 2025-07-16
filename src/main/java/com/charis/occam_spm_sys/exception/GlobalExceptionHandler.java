package com.charis.occam_spm_sys.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.charis.occam_spm_sys.common.Result;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<Result> handleJwtExpired(ExpiredJwtException ex) {
//        return ResponseEntity
//                .status(401)
//                .body(Result.fail(401, "Token已過期，請重新登入"));
//    }
}
