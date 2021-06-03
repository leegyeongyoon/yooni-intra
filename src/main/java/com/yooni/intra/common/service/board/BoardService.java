package com.yooni.intra.common.service.board;

import com.yooni.intra.common.dto.board.ParamsPost;
import com.yooni.intra.common.entity.BoardEntity;
import com.yooni.intra.common.entity.PostEntity;
import com.yooni.intra.common.entity.UserEntity;
import com.yooni.intra.common.repository.BoardJpaRepository;
import com.yooni.intra.common.repository.PostJpaRepository;
import com.yooni.intra.common.repository.UserJpaRepository;
import com.yooni.intra.exception.detailException.CNotOwnerException;
import com.yooni.intra.exception.detailException.CResourceNotExistException;
import com.yooni.intra.exception.detailException.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardJpaRepository boardJpaRepo;
    private final PostJpaRepository postJpaRepo;
    private final UserJpaRepository userJpaRepo;

    // 게시판 이름으로 게시판을 조회. 없을경우 CResourceNotExistException 처리
    public BoardEntity findBoard(String boardName) {
        return Optional.ofNullable(boardJpaRepo.findByName(boardName)).orElseThrow(CResourceNotExistException::new);
    }
    // 게시판 이름으로 게시물 리스트 조회.
    public List<PostEntity> findPosts(String boardName) {
        return postJpaRepo.findByBoard(findBoard(boardName));
    }
    // 게시물ID로 게시물 단건 조회. 없을경우 CResourceNotExistException 처리
    public PostEntity getPost(long postId) {
        return postJpaRepo.findById(postId).orElseThrow(CResourceNotExistException::new);
    }
    // 게시물을 등록합니다. 게시물의 회원UID가 조회되지 않으면 CUserNotFoundException 처리합니다.
    public PostEntity writePost(String uid, String boardName, ParamsPost paramsPost) {
        BoardEntity board = findBoard(boardName);
        PostEntity post = new PostEntity(userJpaRepo.findByUserid(uid).orElseThrow(UserNotFoundException::new), board, paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
        return postJpaRepo.save(post);
    }
    // 게시물을 수정합니다. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    public PostEntity updatePost(long postId, String uid, ParamsPost paramsPost) {
        PostEntity post = getPost(postId);
        UserEntity user = post.getUser();
        if (!uid.equals(user.getUserid()))
            throw new UserNotFoundException();
        post.setUpdate(paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
        return post;
    }
    // 게시물을 삭제합니다. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    public boolean deletePost(long postId, String uid) {
        PostEntity post = getPost(postId);
        UserEntity user = post.getUser();
        if (!uid.equals(user.getUserid()))
            throw new CNotOwnerException();
        postJpaRepo.delete(post);
        return true;
    }
}
