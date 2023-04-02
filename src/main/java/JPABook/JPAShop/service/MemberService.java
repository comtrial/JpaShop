package JPABook.JPAShop.service;

import JPABook.JPAShop.domain.Member;
import JPABook.JPAShop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//COMMENT: @Service 와 @Repository 의 차이
// Repository 는 DB 와 접근하는 로직에 대한 구현으로 이루어져 있고
// Service 는 내부적으로 설계한 비즈니스 로직에 대한 구현으로 이루어져 있다.
// 분리의 이유는 DB 에 대한 접근에 대한 수정이 필요할 경우, 비즈니스 로직의 수정이 필요할 경우의 분리를 통해 확장성을 높히기 위함이만,
// 추후 학습을 통한 서비스 아키텍쳐 설계의 이유에 대해 공부해 봐야하낟.
@Service
@Transactional(readOnly = true)
public class MemberService {
    @Autowired // 왜 프로퍼티 Autowired 를 사용했지..?
    MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    public List<Member> findMemberAll() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(Member member) {
        if (!memberRepository.findByName(member.getName()).isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

}
