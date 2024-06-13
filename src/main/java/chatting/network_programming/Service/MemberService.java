package chatting.network_programming.Service;

import chatting.network_programming.DTO.Login;
import chatting.network_programming.DTO.Member;
import chatting.network_programming.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService{
    private final MemberRepository memberRepository;


    public void save(Member newMbr){ //회원가입 멤버 저장
        memberRepository.save(newMbr);
        log.info("서비스측 회원가입 = {},{},{}", newMbr.getMbrId(), newMbr.getMbrPw(), newMbr.getMbrName());
    }

    public Member login(Login loginMbr){ // 로그인 멤버 확인
        Optional<Member> loginMemberOpt = memberRepository.findByLoginId(loginMbr.getLoginId());

        if (!loginMemberOpt.isPresent()) {
            return null;
        }

        Member loginMember = loginMemberOpt.get();
        if (loginMember.getMbrPw().equals(loginMbr.getLoginPw())) {
            return loginMember;
        } else {
            log.info("XXX");
            return null;
        }
    }

}
