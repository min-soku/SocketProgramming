package chatting.network_programming.Handler;

import chatting.network_programming.DTO.ChatMessage;
import chatting.network_programming.DTO.ChatRoom;
import chatting.network_programming.Service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WebSockChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocket session established with ID: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        try {
            ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
            if (room == null) {
                log.error("Room not found: {}", chatMessage.getRoomId());
                return;
            }

            Set<WebSocketSession> sessions = room.getSessions();

            if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
                sessions.add(session);
                chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
                sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            } else if (chatMessage.getType().equals(ChatMessage.MessageType.QUIT)) {
                sessions.remove(session);
                chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다.");
                sendToEachSocket(sessions, new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            } else {
                sendToEachSocket(sessions, message);
            }
        } catch (Exception e) {
            log.error("Error handling message: {}", e.getMessage(), e);
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    private void sendToEachSocket(Set<WebSocketSession> sessions, TextMessage message) {
        sessions.parallelStream().forEach(roomSession -> {
            try {
                roomSession.sendMessage(message);
            } catch (IOException e) {
                log.error("Error sending message: {}", e.getMessage(), e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocket session closed with ID: {} and status: {}", session.getId(), status);
    }
}
