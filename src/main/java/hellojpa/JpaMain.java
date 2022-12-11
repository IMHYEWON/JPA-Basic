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

            // Sequence 전략을 사용하는 경우,
            // Identity전략과 마찬가지로 영속성 컨텍스트에서 관리할 수 있게 하기 위헤
            // em.persist() 시점에 SEQUENCE 쿼리를 호출해서 컨텍스트에 저장해뒀다가
            // 커밋시점에 트랜잭션을 실행한다 (인서트 쿼리 실행)

            Member user = new Member();
            user.setName("USER");
            user.setAge(25);
            user.setRoleType(RoleType.USER);

            // Sequence 전략 사용시 시퀀스 호출 'call next value for MEMBER_SEQ'
            // MEMBER_SEQ를 호출해서 영속성 컨텍스트에 저장
            // * allocation Size를 50으로 설정했기 때문에 시퀀스 쿼리는 한번만 호출
            em.persist(user);

            // 1차캐시에 저장된 ID (SEQ)
            System.out.println("USER ID : " + user.getId());


            Member user2 = new Member();
            user2.setName("USER2");
            user2.setAge(29);
            user2.setRoleType(RoleType.USER);
            em.persist(user2);  // seq 호출
            System.out.println("USER ID : " + user2.getId());

            Member user3 = new Member();
            user3.setName("USER3");
            user3.setAge(32);
            user3.setRoleType(RoleType.USER);
            em.persist(user3);  // seq 호출
            System.out.println("USER ID : " + user3.getId());

            for (int i = 0; i < 10; i++) {
                em.persist(new Member());
            }

            // Sequence 전략 사용시 트랜잭션 실행 시점
            // SEQ 호출해서 저장된 ID를 사용해 저장
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
