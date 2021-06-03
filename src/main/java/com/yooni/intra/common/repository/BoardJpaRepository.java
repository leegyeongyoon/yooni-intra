package com.yooni.intra.common.repository;

import com.yooni.intra.common.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {
    BoardEntity findByName(String name);
}
