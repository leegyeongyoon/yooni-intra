package com.yooni.intra.common.controller;

import com.yooni.intra.exception.detailException.AuthenticationEntryPointException;
import com.yooni.intra.model.response.CommonResult;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"99. Exception"})
@RestController
//@RequiredArgsConstructor
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public CommonResult enrtypointException(){
        throw new AuthenticationEntryPointException();
    }

    @GetMapping(value = "/accessdenied")
    public CommonResult accessdeniedException(){
        throw new AccessDeniedException("");
    }
}
