package com.yooni.intra.common.repository;

import com.yooni.intra.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

}
