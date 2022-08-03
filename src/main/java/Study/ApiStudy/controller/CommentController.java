package Study.ApiStudy.controller;

import Study.ApiStudy.config.auth.PrincipalDetails;
import Study.ApiStudy.dto.CommentDto;
import Study.ApiStudy.entity.User;
import Study.ApiStudy.repository.UserRepository;
import Study.ApiStudy.response.Response;
import Study.ApiStudy.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    private final UserRepository userRepository;

    // 댓글 작성
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/comments/{boardId}")
    public Response writeComment(@PathVariable("boardId") Integer boardId, @RequestBody CommentDto commentDto, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return new Response("성공","댓글 작성을 완료했습니다.",commentService.writeComment(boardId,commentDto, principalDetails.getUser()));
    }

    // 게시글에 달린 댓글 모두 불러오기
    @ApiOperation(value = "댓글 불러오기",notes = "게시글에 달린 댓글을 모두 불러온다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comments/{boardId}")
    public Response getComments(@PathVariable("boardId") Integer boardId){
        return new Response("성공","댓글을 불러왔습니다.",commentService.getComments(boardId));
    }

    // 댓글 삭제
    @ApiOperation(value = "댓글 삭제하기", notes = "게시글에 달린 댓글을 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/comments/{boardId}/{commentId}")
    public Response deleteComment(@PathVariable("boardId") Integer boardId,@PathVariable("commentId") Integer commentId, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        if(commentService.getComment(commentId).getWriter().equals(principalDetails.getUser().getName())) return new Response("성공","댓글 삭제 완료",commentService.deleteComment(commentId));

        return new Response("실패","댓글 작성자가 아닙니다.",null);
    }



}
