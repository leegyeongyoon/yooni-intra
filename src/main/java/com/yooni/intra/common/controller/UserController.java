package com.yooni.intra.common.controller;

import com.yooni.intra.common.entity.UserEntity;
import com.yooni.intra.common.repository.UserJpaRepository;
import com.yooni.intra.common.service.ResponseService;
import com.yooni.intra.model.response.ListResult;
import com.yooni.intra.model.response.SingleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserJpaRepository userJpaRepository;
    private ResponseService responseService;

    @GetMapping(value = "/user")
    public List<UserEntity> findAll() {
        return userJpaRepository.findAll();
    }

    @PostMapping(value = "/user")
    public UserEntity save() {
        UserEntity userEntity = UserEntity.builder()
                .id("test")
                .name("gyeongyoon")
                .build();
        return userJpaRepository.save(userEntity);
    }
    @GetMapping(value = "/user/{userId}")
    public SingleResult<UserEntity> findUserById(Long id) throws Exception {
        return responseService.getSingleResult(userJpaRepository.findById(id).orElseThrow(Exception::new));
    }
}
