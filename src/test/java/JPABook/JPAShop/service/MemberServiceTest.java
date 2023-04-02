package JPABook.JPAShop.service;

import JPABook.JPAShop.domain.Member;
import JPABook.JPAShop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Test
    public void 회원_등록() throws Exception {
        //given
        Member member = new Member();
        member.setName("choi");

        //when
        Long saveMemberId = memberService.join(member);

        //then
        Assertions.assertThat(member.getId()).isEqualTo(saveMemberId);
    }

    @Test
    public void 회원_중복_검증() throws Exception {
        //given
        String dupName = "choi";

        Member member = new Member();
        member.setName(dupName);
        Long member1 = memberService.join(member);

        //when
        Member member2 = new Member();
        member2.setName(dupName);

        //then
//        memberService.join(member2); // 이때 예외가 발생해야 한다.
        assertThrows(
                IllegalStateException.class,
                () -> {memberService.join(member2);}
        );
    }
}