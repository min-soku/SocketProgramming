package chatting.network_programming.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {
    public enum MessageType { // 메시지 타입 : 입장, 채팅, 나감
        ENTER, TALK, QUIT
    }
    @JsonProperty("type")
    private MessageType type; // 메시지 타입

    @JsonProperty("roomId")
    private String roomId; // 방번호

    @JsonProperty("sender")
    private String sender; // 메시지 발신자

    @JsonProperty("msg")
    private String message; // 메시지
}
