package com.mutsa.market.controller;

import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.exception.Status400Exception;
import com.mutsa.market.exception.Status404Exception;
import com.mutsa.market.exception.Status500Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

        // 400에러 처리
        @ExceptionHandler(Status400Exception.class)
        public ResponseEntity<ResponseDTO> handle400(Status400Exception exception) {
            ResponseDTO response = new ResponseDTO();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // 404에러 처리
        @ExceptionHandler(Status404Exception.class)
        public ResponseEntity<ResponseDTO> handle404(Status404Exception exception) {
            ResponseDTO response = new ResponseDTO();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // 500에러 처리
        @ExceptionHandler(Status500Exception.class)
        public ResponseEntity<ResponseDTO> handle500(Status500Exception exception) {
            ResponseDTO response = new ResponseDTO();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
