package jpashop;

import hellojpa.Member;
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

            Member user = new Member();
            em.persist(user);
			System.out.println("USER ID : " + user.getId());
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}