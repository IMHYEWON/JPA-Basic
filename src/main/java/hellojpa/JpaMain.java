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
			
			//Identity전략을 사용했을 때, 동작 방식은
			//어플리케이션에서 직접 값을 넣지 않고, 
			//null상태로 insert 쿼리를 날리면 DB에서 id값을 세팅해줌
			//-> DB에 값이 저장 된 후 id 값을 알 수 있다!

			//그런데 영속성 컨텍스트에서 객체를 관리하려면, PK값이 있어야됨 (1차캐시에 저장하기 위해서는)
			///-> 그래서 이 경우에, (커밋 시점이 아닌) em.persist()시점에서 insert 쿼리를 호출한다

            Member user = new Member(5L);
            user.setName("USER");
            user.setAge(25);
            user.setRoleType(RoleType.USER);

			// identity 전략 사용시 객체 저장할 때 트랜잭션 실행 시점
            em.persist(user);

			// Insert 시점에 값을 리턴하는 JDBC 내부 API가 있어서 값 호출 가능
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
