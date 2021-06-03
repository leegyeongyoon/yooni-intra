package com.yooni.intra.common.controller;

import com.yooni.intra.common.dto.board.ParamsPost;
import com.yooni.intra.common.entity.BoardEntity;
import com.yooni.intra.common.entity.PostEntity;
import com.yooni.intra.common.service.ResponseService;
import com.yooni.intra.common.service.board.BoardService;
import com.yooni.intra.model.response.CommonResult;
import com.yooni.intra.model.response.ListResult;
import com.yooni.intra.model.response.SingleResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"3. Board"})
@RestController
@RequestMapping(value = "yooni/board")
public class BoardController {
    @Autowired
    private  BoardService boardService;
    @Autowired
    private  ResponseService responseService;

    @ApiOperation(value = "게시판 정보 조회", notes = "게시판 정보를 조회한다.")
    @GetMapping(value = "/{boardName}")
    public SingleResult<BoardEntity> boardInfo(@PathVariable String boardName){
        return responseService.getSingleResult(boardService.findBoard(boardName));
    }

    @ApiOperation(value = "게시글 리스트", notes = "게시글 리스트를 조회한다.")
    @GetMapping(value = "/{boardName}/posts")
    public ListResult<PostEntity> posts(@PathVariable String boardName){
        return responseService.getListResult(boardService.findPosts(boardName));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access-token", required = true,dataType = "String",paramType = "header")
    })
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.")
    @PostMapping(value = "/{boardName}")
    public SingleResult<PostEntity> post(@PathVariable String boardName, @Valid @ModelAttribute ParamsPost post){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        return responseService.getSingleResult(boardService.writePost(userid, boardName, post));
    }

    @ApiOperation(value = "게시글 상세", notes = "게시글 상세보기를 조회한다.")
    @PostMapping(value = "/post/{postId}")
    public SingleResult<PostEntity> post(@PathVariable long postId){
        return responseService.getSingleResult(boardService.getPost(postId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access-token", required = true,dataType = "String",paramType = "header")
    })
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    @PutMapping(value = "/post/{postId}")
    public SingleResult<PostEntity> post(@PathVariable long postId, @Valid @ModelAttribute ParamsPost post){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        return responseService.getSingleResult(boardService.updatePost(postId, userid, post));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access-token", required = true,dataType = "String",paramType = "header")
    })
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    @DeleteMapping(value = "/post/{postId}")
    public CommonResult deletePost(@PathVariable long postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        boardService.deletePost(postId,userid);
        return responseService.getSuccessResult();
    }



}
