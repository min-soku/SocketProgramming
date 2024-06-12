package chatting.network_programming.Service;

import chatting.network_programming.DTO.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper; // 채팅 서비스에 필요한 JSON처리를 위해 필요
    private Map<String, ChatRoom> chatRooms; // 채팅방ID - 채팅방을 묶어서 저장

    @PostConstruct
    private void init(){
        chatRooms = new LinkedHashMap<>(); // 채팅방을 저장할 때 삽입 순서를 유지함
    }

    public List<ChatRoom> findAllRoom(){ // 모든 채팅방 반환
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId){ // 채팅방 조회
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name){ // 채팅방 생성
        String randomId = UUID.randomUUID().toString(); // 랜덤값 생성하여 고유한 채팅방ID 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId,chatRoom);
        return chatRoom;
    }
}
