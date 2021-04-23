package com.yooni.intra.common.controller;

import com.yooni.intra.common.entity.UserEntity;
import com.yooni.intra.common.repository.UserJpaRepository;
import com.yooni.intra.common.service.ResponseService;
import com.yooni.intra.config.security.JwtTokenProvider;
import com.yooni.intra.exception.detailException.UserNotFoundException;
import com.yooni.intra.model.response.CommonResult;
import com.yooni.intra.model.response.ListResult;
import com.yooni.intra.model.response.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Api(tags = {"1. User"})
@RestController
@RequestMapping(value = "yooni")
public class UserController {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping(value = "/user")
    public List<UserEntity> findAll() {
        return userJpaRepository.findAll();
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입.")
    @PostMapping(value = "/user")
    public CommonResult save(@ApiParam(value = "회원 ID", required = true) @RequestParam String username, @ApiParam(value = "회원 PW", required = true) @RequestParam String password , @ApiParam(value = "회원 NAME", required = true) @RequestParam String name) {
        System.out.println("username : " + username+ "password : " + password+"name : " + name);
        userJpaRepository.save(UserEntity.builder()
                .userid(username)
                .name(name)
                .password(password)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "특정 회원 조회", notes = "특정 회원을 조회합니다.")
    @GetMapping(value = "/user/{userid}")
    public SingleResult<UserEntity> findUserById(@ApiParam(value = "회원 ID", required = true) @PathVariable String userid ,@ApiParam(value = "lang", defaultValue = "ko") @RequestParam String lang) {
        //return userJpaRepository.findByUserid(userid);
        return responseService.getSingleResult(userJpaRepository.findByUserid(userid).orElseThrow(UserNotFoundException::new));
    }
}
