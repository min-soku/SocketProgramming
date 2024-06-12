package chatting.network_programming.Controller;

import chatting.network_programming.DTO.ChatRoom;
import chatting.network_programming.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/chatList")
    public String chatList(Model model){
        List<ChatRoom> roomList = chatService.findAllRoom();
        model.addAttribute("roomList", roomList);
        return "chat/chatList";
    }

    @PostMapping("/createRoom")
    public String createRoom(Model model, @RequestParam("name") String name, @RequestParam("username") String username){
        log.info("채팅방 생성 메소드");
        ChatRoom room = chatService.createRoom(name);
        model.addAttribute("room", room);
        model.addAttribute("username", username);
        log.info("방 생성 = {}, 이름 = {}", room, username);
        return "chat/chatRoom";
    }

    @GetMapping("/chatRoom")
    public String chatRoom(Model model, @RequestParam("roomId") String roomId, @RequestParam("username") String username){
        ChatRoom room = chatService.findRoomById(roomId);
        model.addAttribute("room", room);
        model.addAttribute("username", username);
        return "chat/chatRoom";
    }
}
