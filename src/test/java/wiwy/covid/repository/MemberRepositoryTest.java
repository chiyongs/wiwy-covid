package wiwy.covid.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import wiwy.covid.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberService memberService;

    @Test
    void 회원이름으로조회() {
        UserDetails bbb = memberService.loadUserByUsername("1234");
    }

}