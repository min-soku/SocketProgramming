package chatting.network_programming.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {
    private String roomId; // 채팅방 고유번호
    private String name; // 채팅방 이름
    private Set<WebSocketSession> sessions = new HashSet<>(); // 채팅방 사용자 저장

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }
}
