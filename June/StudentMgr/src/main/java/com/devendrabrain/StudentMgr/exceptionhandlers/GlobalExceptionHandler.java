package com.devendrabrain.StudentMgr.exceptionhandlers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = APIGenericValidationError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorType.Error handleFieldValidationError(APIGenericValidationError apiGenericValidationError, WebRequest webRequest) {

        log.error("Log Root cause in handleFieldValidationError "+ ExceptionUtils.getRootCause(apiGenericValidationError));

        ErrorType.Error error = ErrorType.Error.builder().developerMessage(apiGenericValidationError.getErrorMessage()).
                code(apiGenericValidationError.getCode()).message(apiGenericValidationError.getMessage()).
                timestamp(new Date().toString()).status(Integer.toString(HttpStatus.BAD_REQUEST.value())).build();

        return error;
    }


}
