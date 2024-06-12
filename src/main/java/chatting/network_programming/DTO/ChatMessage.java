package chatting.network_programming.DTO;

import lombok.Data;

@Data
public class ChatMessage {
    public enum MessageType { // 메시지 타입 : 입장, 채팅, 나감
        ENTER, TALK, QUIT
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 발신자
    private String message; // 메시지
}
