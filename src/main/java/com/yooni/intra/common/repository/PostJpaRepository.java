package com.yooni.intra.common.repository;

import com.yooni.intra.common.entity.BoardEntity;
import com.yooni.intra.common.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByBoard(BoardEntity board);
}
