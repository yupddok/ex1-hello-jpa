package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // persistenceUnitName
        // /src/main/resources/META-INF/persistence.xml
        // <persistence-unit name="hello">
        // 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다).
        EntityManager em = emf.createEntityManager();

        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityTransaction tx = em.getTransaction();

        // 엔티티매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
        tx.begin();

        try {
            /*// 등록

            // 비영속 : 객체를 생성한 상태
            Member member = new Member();
            member.setId(3L);
            member.setName("helloC");

            // 영속 : 객체를 저장한 상태
            em.persist(member);

            // 준영속 : 회원 엔티티를 영속성 컨텍스트에서 분리
            em.detach(member);

            // 준영속 : 영속성 컨텍스트를 완전히 초기화
            em.clear();

            // 조회
            Member findMember = em.find(Member.class, 3L);

            // 수정
            findMember.setName("helloC-수정");

            // 삭제
            em.remove(findMember);*/

            // JPQL로 전체 회원 검색 + 페이징
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());
//            }


            // 영속 엔티티의 동일성 보장
            // 1차 캐시로 반복 가능한 읽기 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공
            Member a = em.find(Member.class, 1L);
            Member b = em.find(Member.class, 1L);
            System.out.println("a == b = " +( a == b));

            // 커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            // 준영속 : 영속성 컨텍스트를 종료
            em.close();
        }
        emf.close();
    }
}
