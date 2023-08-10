package com.mobiusp.server;

import com.mobiusp.server.pojo.Result;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @CrossOrigin(value = {"http://mobiusp.cc","http://www.mobiusp.cc"})
    @ExceptionHandler({Exception.class})
    public Result unknownHandler(Exception e) {
        logger.error("There is an unknown exception. " + e.getMessage());
        return new Result(500, "Sever error.",null);
    }
}
