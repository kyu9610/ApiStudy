package controller;

import dto.BoardDto;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import repository.UserRepository;
import response.Response;
import service.BoardService;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final UserRepository userRepository;

    // 전체 게시글 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards")
    public Response getBoards(){
        return new Response("성공","전체 게시물 리턴",boardService.getBoards());
    }

    // 개별 게시글 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards/{id}")
    public Response getBoard(@PathVariable("id") Integer id){
        return new Response("성공","개별 게시물 리턴",boardService.getBoard(id));
    }

    // 게시글 작성
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards/write")
    public Response write(@RequestBody BoardDto boardDto){
        User user = userRepository.findById(1).get();
        return new Response("성공","글 작성 성공",boardService.write(boardDto,user));
    }

    // 게시글 수정
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards/update/{id}")
    public Response edit(@RequestBody BoardDto boardDto, @PathVariable("id") Integer id){
        User user = userRepository.findById(1).get(); // 임시로
        return new Response("성공","글 수정 성공",boardService.update(id,boardDto));
    }

    // 게시글 삭제
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boards/delete/{id}")
    public Response delete(@PathVariable("id") Integer id){
        boardService.delete(id);
        return new Response("성공","글 삭제 성공",null);
    }
}
