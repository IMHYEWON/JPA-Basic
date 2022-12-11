package jpashop;

import java.util.List;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpashop.domain.Enum.OrderStatus;
import jpashop.domain.Item;
import jpashop.domain.Member;
import jpashop.domain.Order;
import jpashop.domain.OrderItem;
import jpashop.domain.Team;

public class JpaMain {

    final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    final static EntityManager em = emf.createEntityManager();
    final static EntityTransaction tx = em.getTransaction();

    private static void setData() {

        Team team = new Team();
        team.setName("TEAM A");
        em.persist(team);

        Member user = new Member();
        user.setCity("SEOUL");
        user.setStreet("93");
        user.setTeam(team);
        em.persist(user);
        Long tempUserId = user.getId();

        Member user2 = new Member();
        user2.setCity("NEWYORK");
        user2.setTeam(team);
        em.persist(user2);

        em.flush();
        em.clear();
        //// TEAM - MEMBER 양방향 연관관계 매핑
        Member member = em.find(Member.class, tempUserId);
        List<Member> members = member.getTeam().getMembers();
        System.out.println("MEMBER TEAM NAME : " + member.getTeam().getName());

        for (Member m : members) {
            System.out.println("Member of Teams : " + m.getId());
        }

        Item book = new Item("BOOK", 30000, 530);
        em.persist(book);

        Order order = new Order(user.getId(), LocalDate.now(), OrderStatus.ORDER);
        em.persist(order);

        Integer count = 2;
        OrderItem orderItem = new OrderItem(order.getId(), book.getId(), book.getPrice() * count, count);
        em.persist(orderItem);

        tx.commit();
    }

    public static void main(String[] args) {

        // 트랜잭션 시작
        tx.begin();
        try {
            setData();

            // if ) 관계 매핑 X, 객체설계를 테이블 설계에 맞춘 방식
            // 객체지향적이지 않은 코드
            // 이 방식으로 테이블을 조회할 때 프로세스

            // 1. Order찾아서 member Id 얻기
            Order order = em.find(Order.class, 1L);
            Long memberId = order.getMemberId();
            System.out.println("MEMBER ID : " + memberId);

            // 2. 찾은 member Id로 Member 찾기
            Member member = em.find(Member.class, memberId);
            System.out.println("MEMBER CITY : " + member.getCity());


        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
