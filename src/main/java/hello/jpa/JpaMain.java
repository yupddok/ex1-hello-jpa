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
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            // 양방향 매핑시 연관관계의 주인에 값을 입력해야 한다.
//            member.changeTeam(team);   //**
            em.persist(member);

            team.addMember(member);

            // 연관관계 주인이 아니면 값을 입력해야하나 말아야하나 ?
            // db 관점에서는 안써됨
            // 순수한 객체 관계를 고려하면 항상 양쪽 다 값을 입력해야한다.
            // test case를 위해서도 (jpa 없이) 양쪽 다 값을 입력해야한다.
            // 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자
            // -> 실수 방지를 위해 연관관계 편의 메소드를 생성하자
//            team.getMembers().add(member);   //**
            
//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());

            // em.flush(), em.clear() 후에는 상관없으나 1차 캐시에서 가져올 경우
            // team.getMembers().add(member); 이 코드가 없으면
            // 에러남
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m.getName() = " + m.getName());
            }



//            // 커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
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
