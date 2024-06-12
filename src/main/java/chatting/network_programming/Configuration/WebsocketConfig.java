package chatting.network_programming.Configuration;

import chatting.network_programming.Handler.WebSockChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket // WenSocket을 활성화한다.
public class WebsocketConfig implements WebSocketConfigurer {
    private final WebSockChatHandler webSockChatController;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSockChatController, "/ws/chat").setAllowedOrigins("*");
        // '/ws/chat'경로에 웹소켓 연결을 처리하는 Handler등록, js에서 new WebSocket할 때 경로 지정
        // 다른 URL에서도 접속할 수 있게함
    }
}
