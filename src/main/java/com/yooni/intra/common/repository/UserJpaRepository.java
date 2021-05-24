package com.yooni.intra.common.repository;

import com.yooni.intra.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserid(String userid);
    Optional<UserEntity> findByUseridAndProvider(String userid,String provider);

}

