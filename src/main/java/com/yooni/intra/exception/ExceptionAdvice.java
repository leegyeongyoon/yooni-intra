package com.yooni.intra.exception;

import com.yooni.intra.exception.detailException.UserNotFoundException;
import com.yooni.intra.model.response.CommonResult;
import com.yooni.intra.common.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e){
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")),getMessage("unKnown.msg"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFountException(HttpServletRequest request, UserNotFoundException e){
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")),getMessage("userNotFound.msg"));
    }

    private String getMessage(String code){
        return getMessage(code,null);
    }
    private String getMessage(String code,Object [] args){
        return messageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }
}
