package com.junehouse.controller;

import com.junehouse.exception.JuneTopException;
import com.junehouse.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
//        ErrorResponse errorResponse = new ErrorResponse("400", "잘못된 요청입니다.");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorResponse;
    }

    /*Map<String, String> response = new HashMap<>();
    response.put(field, defaultMessage);*/
    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(JuneTopException.class)
    public ResponseEntity<ErrorResponse> PostNotFound(JuneTopException e) {
        ErrorResponse body = ErrorResponse.builder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .validation(e
                        .getValidation())
                .build();

        /*if (e instanceof InvalidRequest) {
            InvalidRequest invalidRequest = (InvalidRequest) e;
            body.addValidation(invalidRequest.getFieldName(), invalidRequest.getFieldMessage());
        }*/

        return ResponseEntity.status(Integer.parseInt(e.getStatusCode())).body(body);
    }
}
