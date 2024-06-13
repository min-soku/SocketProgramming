package chatting.network_programming.DTO;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String mbrId;
    private String mbrPw;
    private String mbrName;
}
