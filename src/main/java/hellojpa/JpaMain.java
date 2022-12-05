package hellojpa;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        // 트랜잭션 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 1차 캐시 : 한 트랜잭션 안에서만 저장되는 캐시기 때문에 성능에 큰 도움은 안된다
            // 1차 캐시 저장 전, DB에서 조회해서 1차 캐시에 저장
            Member memberBefore = em.find(Member.class, 1L);
            System.out.println("member.name = " + memberBefore.getName());

            // 1차 캐시 저장 후, 1차 캐시에서 찾아서 반환
            Member memberAfter = em.find(Member.class, 1L);
            System.out.println("member.name = " + memberAfter.getName());

            // 1차 캐시 저장 전, DB에서 조회해서 1차 캐시에 저장
            Member memberOther = em.find(Member.class, 2L);
            System.out.println("member.name = " + memberOther.getName());

            // 비영속 상태, 객체 생성
            Member member = new Member(4L, "HelloD");

            // 영속 상태, 컨텍스트에 저장 & 1차 캐시에 저장
            em.persist(member);

            // 1차 캐시에서 조회 후 반환함
            Member memberNew = em.find(Member.class, 4L);

            System.out.println("new member.id = " + memberNew.getId());
            System.out.println("new member.name = " + memberNew.getName());

            // 커밋 시 저장 쿼리 실행 됨
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
