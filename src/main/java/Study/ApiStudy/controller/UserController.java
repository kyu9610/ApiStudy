package Study.ApiStudy.controller;

import Study.ApiStudy.dto.RegisterDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import Study.ApiStudy.response.Response;
import Study.ApiStudy.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "전체 유저 보기", notes = "전체 유저를 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public Response<?> findAll(){
        return new Response<>("true","조회 성공",userService.findAll());
    }

    @ApiOperation(value = "개별 유저 보기", notes = "개별 유저를 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public Response<?> findUser(@PathVariable("id") Integer id){
        return new Response<>("true","조회 성공",userService.findUser(id));
    }

    @ApiOperation(value = "회원 가입 하기", notes = "회원 가입을 한다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth")
    public Response<?> register(@RequestBody RegisterDto registerDto){
        return new Response<>("true","가입 성공",userService.register(registerDto));
    }
}
