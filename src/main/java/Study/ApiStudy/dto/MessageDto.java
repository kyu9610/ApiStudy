package Study.ApiStudy.dto;

import Study.ApiStudy.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String title;
    private String content;
    private String senderName;
    private String receiverName;

    public static MessageDto toDto(Message message){
        return new MessageDto(
                message.getTitle(),
                message.getContent(),
                message.getSender().getName(),
                message.getReceiver().getName()
        );
    }
}
