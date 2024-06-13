package chatting.network_programming.Repository;

import chatting.network_programming.DTO.Login;
import chatting.network_programming.DTO.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용

    public Member save(Member newMbr){
        newMbr.setId(++sequence);
        store.put(newMbr.getId(), newMbr);
        log.info("리포지토리측 회원가입 = {},{},{}", newMbr.getMbrId(), newMbr.getMbrPw(), newMbr.getMbrName());
        return newMbr;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getMbrId().equals(loginId))
                .findFirst();
    }
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    public void clearStore() {
        store.clear();
    }
}
