package com.yooni.intra.common.controller;

import com.yooni.intra.common.entity.UserEntity;
import com.yooni.intra.common.repository.UserJpaRepository;
import com.yooni.intra.common.service.ResponseService;
import com.yooni.intra.exception.detailException.UserNotFoundException;
import com.yooni.intra.model.response.ListResult;
import com.yooni.intra.model.response.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping(value = "/user")
    public List<UserEntity> findAll() {
        return userJpaRepository.findAll();
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입.")
    @PostMapping(value = "/user")
    public UserEntity save() {
        UserEntity userEntity = UserEntity.builder()
                .userid("test")
                .name("gyeongyoon")
                .build();
        return userJpaRepository.save(userEntity);
    }

    @ApiOperation(value = "특정 회원 조회", notes = "특정 회원을 조회합니다.")
    @GetMapping(value = "/user/{userid}")
    public SingleResult<UserEntity> findUserById(@ApiParam(value = "회원 ID", required = true) @PathVariable String userid) {
        //return userJpaRepository.findByUserid(userid);
        return responseService.getSingleResult(userJpaRepository.findByUserid(userid).orElseThrow(UserNotFoundException::new));
    }
}
