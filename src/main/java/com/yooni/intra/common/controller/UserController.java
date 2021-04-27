package com.yooni.intra.common.controller;

import com.yooni.intra.common.entity.UserEntity;
import com.yooni.intra.common.repository.UserJpaRepository;
import com.yooni.intra.common.service.ResponseService;
import com.yooni.intra.config.security.JwtTokenProvider;
import com.yooni.intra.exception.detailException.SignFailedException;
import com.yooni.intra.exception.detailException.UserNotFoundException;
import com.yooni.intra.model.response.CommonResult;
import com.yooni.intra.model.response.ListResult;
import com.yooni.intra.model.response.SingleResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping(value = "/login")
    public SingleResult<String> login(@ApiParam(value = "회원 ID", required = true) @RequestParam String userid, @ApiParam(value = "회원 PW", required = true) @RequestParam String password, @ApiParam(value = "lang", defaultValue = "ko") @RequestParam String lang) {
        UserEntity userEntity = userJpaRepository.findByUserid(userid).orElseThrow(SignFailedException::new);
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new SignFailedException();
        }
        return responseService.getSingleResult(jwtTokenProvider.createToken(userEntity.getUserid(), userEntity.getRoles()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping(value = "/users")
    public ListResult<UserEntity> findAll() {
        return responseService.getListResult(userJpaRepository.findAll());
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입.")
    @PostMapping(value = "/join")
    public CommonResult save(@ApiParam(value = "회원 ID", required = true) @RequestParam String username, @ApiParam(value = "회원 PW", required = true) @RequestParam String password, @ApiParam(value = "회원 NAME", required = true) @RequestParam String name) {
        userJpaRepository.save(UserEntity.builder()
                .userid(username)
                .name(name)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return responseService.getSuccessResult();
    }
//@ApiParam(value = "회원 ID", required = true) @PathVariable String userid,
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "특정 회원 조회", notes = "특정 회원을 조회합니다.")
    @GetMapping(value = "/user")
    public SingleResult<UserEntity> findUserById(@ApiParam(value = "lang", defaultValue = "ko") @RequestParam String lang) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        System.out.println("id :" +id);
        return responseService.getSingleResult(userJpaRepository.findByUserid(id).orElseThrow(UserNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원 수정.")
    @PutMapping(value = "/user")
    public SingleResult<UserEntity> modify(@ApiParam(value = "회원 번호", required = true) @RequestParam Long no , @ApiParam(value = "회원 ID", required = true) @RequestParam String userid, @ApiParam(value = "회원 PW", required = true) @RequestParam String password, @ApiParam(value = "회원 NAME", required = true) @RequestParam String name) {
        UserEntity data =userJpaRepository.findByUserid(userid).orElseThrow(UserNotFoundException::new);
        UserEntity userEntity = UserEntity.builder()
                .no(no)
                .userid(userid)
                .name(name)
                .password(passwordEncoder.encode(password))
                .roles(data.getRoles())
                .build();
        return responseService.getSingleResult(userJpaRepository.save(userEntity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원 삭제.")
    @DeleteMapping(value = "/user/{no}")
    public CommonResult delete(@ApiParam(value = "회원 번호", required = true) @RequestParam Long no) {
        userJpaRepository.deleteById(no);
        return responseService.getSuccessResult();
    }
}
