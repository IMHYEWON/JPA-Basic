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
            Member user = new Member(5L);
            user.setName("USER");
            user.setAge(25);
            user.setRoleType(RoleType.USER);

            em.persist(user);

            Member admin = new Member(6L);
            admin.setName("ADMIN");
            admin.setAge(32);
            admin.setRoleType(RoleType.ADMIN);

            em.persist(admin);

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
