package Study.ApiStudy.service;

import Study.ApiStudy.dto.MessageDto;
import Study.ApiStudy.entity.Message;
import Study.ApiStudy.entity.User;
import Study.ApiStudy.repository.MessageRepository;
import Study.ApiStudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageDto write(MessageDto messageDto){
        User receiver = userRepository.findByName(messageDto.getReceiverName());
        User sender = userRepository.findByName(messageDto.getSenderName());

        Message message = new Message();
        message.setReceiver(receiver);
        message.setSender(sender);

        message.setTitle(messageDto.getTitle());
        message.setContent(messageDto.getContent());
        message.setDeleteByReceiver(false);
        message.setDeleteBySender(false);
        messageRepository.save(message);

        return MessageDto.toDto(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> receivedMessage(User user){
        List<Message> messages = messageRepository.findAllByReceiver(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(Message message : messages){
            if(!message.isDeleteByReceiver()){
                messageDtos.add(MessageDto.toDto(message));
            }
        }

        return messageDtos;
    }

    @Transactional
    public Object deleteMessageByReceiver(int id,User user){
        Message message = messageRepository.findById(id).orElseThrow(()->{
           return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
        });

        if(user == message.getSender()){
            message.deleteByReceiver();
            if(message.isDeleted()){
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        }else{
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<MessageDto> sentMessage(User user){
        List<Message> messages = messageRepository.findAllBySender(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(Message message : messages){
            if(!message.isDeleteBySender()){
                messageDtos.add(MessageDto.toDto(message));
            }
        }

        return messageDtos;
    }

    @Transactional
    public Object deleteMessageBySender(int id,User user){
        Message message = messageRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
        });

        if(user == message.getSender()) {
            message.deleteBySender(); // 받은 사람에게 메시지 삭제
            if (message.isDeleted()) {
                // 받은사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제요청
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
    }
}
