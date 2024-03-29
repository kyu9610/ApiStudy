package Study.ApiStudy.controller;

import Study.ApiStudy.config.auth.PrincipalDetails;
import Study.ApiStudy.dto.BoardDto;
import Study.ApiStudy.entity.User;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import Study.ApiStudy.repository.UserRepository;
import Study.ApiStudy.response.Response;
import Study.ApiStudy.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    private final UserRepository userRepository;

    // 전체 게시글 조회
    @ApiOperation(value = "전체 게시글 보기", notes = "전체 게시글을 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards")
    public Response getBoards(){
        return new Response("성공","전체 게시물 리턴",boardService.getBoards());
    }

    // 개별 게시글 조회
    @ApiOperation(value = "개별 게시글 보기", notes = "개별 게시글을 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/board/{id}")
    public Response getBoard(@PathVariable("id") Integer id){
        return new Response("성공","개별 게시물 리턴",boardService.getBoard(id));
    }

    // 게시글 작성
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/board/write")
    public Response write(@RequestBody BoardDto boardDto, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        return new Response("성공","글 작성 성공",boardService.write(boardDto,user));
    }

    // 게시글 수정
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/board/update/{id}")
    public Response edit(@RequestBody BoardDto boardDto, @PathVariable("id") Integer id, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        if(user.getName().equals(boardService.getBoard(id).getWriter())) return new Response("성공","글 수정 성공",boardService.update(id,boardDto));
        else return new Response("실패", "본인 게시물만 수정 할 수 있습니다.",null);
    }

    // 게시글 삭제
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/board/delete/{id}")
    public Response delete(@PathVariable("id") Integer id,Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();
        if(user.getName().equals(boardService.getBoard(id).getWriter())){
            boardService.delete(id);
            return new Response("성공","글 삭제 성공",null);
        }else{
            return new Response("실패","본인 게시글만 삭제할 수 있습니다.",null);
        }
    }
}
