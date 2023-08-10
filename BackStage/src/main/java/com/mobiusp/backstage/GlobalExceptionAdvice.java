package com.mobiusp.backstage;

import com.mobiusp.backstage.pojo.Result;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @CrossOrigin("http://mobiusp.cc:3939/")
    @ExceptionHandler({Exception.class})
    public Result unknownHandler(Exception e) {
        logger.error("There is an unknown exception. " + e.getMessage());
        return new Result(500, "Sever error.",null);
    }
}
