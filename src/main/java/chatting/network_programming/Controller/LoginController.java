package chatting.network_programming.Controller;

import chatting.network_programming.DTO.Login;
import chatting.network_programming.DTO.Member;
import chatting.network_programming.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/member/add") // 회원가입
    public String addMbr(@ModelAttribute("member") Member member){
        return "member/addForm"; // 회원가입 폼으로 이동
    }

    @PostMapping("/member/add")
    public String save(@ModelAttribute("member") Member member, Model model){
        memberService.save(member);
        return "redirect:/";
    }

    @GetMapping("/") // 홈화면 로그인
    public String loginForm(Model model){
        model.addAttribute("loginMbr", new Login());
        log.info("홈화면이동");
        return "member/loginForm";
    }

    @PostMapping("/")
    public String login(@ModelAttribute("loginMbr") Login loginMbr, BindingResult bindingResult, HttpServletRequest request){
        log.info("로그인 입력정보 = {},{}", loginMbr.getLoginId(), loginMbr.getLoginPw());
        Member mbr = memberService.login(loginMbr);
        if(mbr == null){ //없는 회원일 때
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/loginForm";
        }
        HttpSession session =request.getSession();
        session.setAttribute("loginMbr", mbr);
        return "redirect:chat/home";
    }


}
