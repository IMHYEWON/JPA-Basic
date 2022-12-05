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
            // 기본 JPQL 작성법, 일반적인 쿼리와 다르게 JPA 관점에서 Member 객체를 조회
            List<Member> findMembers = em.createQuery("select m from Member as m", Member.class)
                // 조회된 결과의 1~10 / offset, limit 설정
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();
            for (Member member : findMembers) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
