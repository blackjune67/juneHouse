package com.junehouse.controller;

import com.junehouse.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@RestController
public class PostController {
    // SSR => jsp, thymeleaf
    // SPA => vue + SSR = nuxt.js, react + SSR = next.js

    // * GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate params, BindingResult result) {
        log.info("params = " + params.toString());
        if(result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();

            HashMap<String, String> error = new HashMap<>();
            error.put(field, defaultMessage);
            return error;
        }
        return Map.of();
//        return new HashMap<>();
    }

}
