package Study.ApiStudy.controller;

import Study.ApiStudy.dto.MessageDto;
import Study.ApiStudy.entity.User;
import Study.ApiStudy.repository.UserRepository;
import Study.ApiStudy.response.Response;
import Study.ApiStudy.service.MessageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserRepository userRepository;

    @ApiOperation(value = "쪽지 보내기", notes = "쪽지 보내기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/messages")
    public Response<?> sendMessage(@RequestBody MessageDto messageDto){
        User user = userRepository.findById(1).orElseThrow(() -> {
           return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });
        messageDto.setSenderName(user.getName());

        return new Response<>("성공","쪽지를 보냈습니다.",messageService.write(messageDto));
    }

    @ApiOperation(value = "받은 편지함 읽기", notes = "받은 편지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/received")
    public Response<?> getMessage(){
        User user = userRepository.findById(2).orElseThrow(() -> {
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });

        return new Response<>("성공","받은 쪽지를 불러왔습니다.",messageService.receivedMessage(user));
    }

    @ApiOperation(value = "받은 쪽지 삭제하기", notes = "받은 쪽지를 삭제합니다")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/received/{id}")
    public Response<?> deleteReceivedMessage(@PathVariable("id") Integer id){
        User user = userRepository.findById(1).orElseThrow(() -> {
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });

        return new Response<>("삭제 성공","받은 쪽지인, " + id + "번 쪽지를 삭제했습니다.", messageService.deleteMessageByReceiver(id,user));
    }

    @ApiOperation(value = "보낸 편지함 읽기", notes = "보낸 편지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/sent")
    public Response<?> getSentMessage(){
        User user = userRepository.findById(1).orElseThrow(() -> {
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });

        return new Response<>("성공","보낸 쪽지를 불러왔습니다.",messageService.sentMessage(user));
    }

    @ApiOperation(value = "보낸 쪽지 삭제하기", notes = "보낸 쪽지를 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/sent/{id}")
    public Response<?> deleteSentMessage(@PathVariable("id") Integer id){
        User user = userRepository.findById(1).orElseThrow(() -> {
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });

        return new Response<>("성공","보낸 쪽지인, " + id + "번 쪽지를 삭제했습니다.",messageService.deleteMessageBySender(id,user));
    }
}