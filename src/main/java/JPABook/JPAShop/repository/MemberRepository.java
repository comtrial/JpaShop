package JPABook.JPAShop.repository;

import JPABook.JPAShop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//TODO: Transactional 이 Repository 에 붙는게 아니고 Service 에 붙는 이유
// -> Transactional 의 깊은 이해는 부족하지만, Transactional 이 하나의 작업에 대한 관리를 해주고
// 해당 작업의 Rollback 또한 진행해 주기에, 하나의 Repository 에서의 Transactional 작업을 해주기 보다는
// 비즈니스 로직의 시작점인 @Service 에서 Transactional 을 해주는 것이 보다 상위 작업에 대한 관리가 가능해진다.
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    // 아래와 같이 생성자를 통한 의존성 주입도 가능하지만, class 상단에 @RequiredArgsConstructor 를 통해
    // 생성자 없이도 Lombok 이 해결 할 수 있게 할 수도 있다.
    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("selct m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
