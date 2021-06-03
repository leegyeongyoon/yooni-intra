package com.yooni.intra.common.entity;

import com.yooni.intra.common.entity.BoardEntity;
import com.yooni.intra.common.entity.CommonDateEntity;
import com.yooni.intra.common.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostEntity extends CommonDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Column(nullable = false, length = 50)
    private String author;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board; // 게시글 - 게시판의 관계 - N:1


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "msrl")
    private UserEntity user;  // 게시글 - 회원의 관계 - N:1

    // Join 테이블이 Json결과에 표시되지 않도록 처리.
    protected BoardEntity getBoard() {
        return board;
    }

    // 생성자
    public PostEntity(UserEntity user, BoardEntity board, String author, String title, String content) {
        this.user = user;
        this.board = board;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    // 수정시 데이터 처리
    public PostEntity setUpdate(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        return this;
    }
}
