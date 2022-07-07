package Study.ApiStudy.controller;

import Study.ApiStudy.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import Study.ApiStudy.response.Response;
import Study.ApiStudy.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public Response<?> home(){
        return new Response<>("true","조회 성공",null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public Response<?> findAll(){
        return new Response<>("true","조회 성공",userService.findAll());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public Response<?> findUser(@PathVariable("id") Integer id){
        return new Response<>("true","조회 성공",userService.findUser(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/auth")
    public Response<?> register(@RequestBody RegisterDto registerDto){
        return new Response<>("true","가입 성공",userService.register(registerDto));
    }
}
