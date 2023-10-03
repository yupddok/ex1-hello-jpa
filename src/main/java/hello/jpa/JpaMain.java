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
//            Member_bk member = new Member_bk();
////            member.setId(4L);
//            member.setUsername("C");
//            member.setRoleType(RoleType.GUEST);
//            em.persist(member);

            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
//            member.setTeamId(team.getId());
            member.setTeam(team);
            em.persist(member);
            
            // 영속성 컨텍스트 초기화
            em.flush();
            em.clear();
            
            Member findMember = em.find(Member.class, member.getId());
            // 양방향 매핑
            List<Member> members = findMember.getTeam().getMembers();
            for (Member member1 : members) {
                System.out.println("member1 = " + member1.getName());
            }

//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamId);
            Team findTeam = findMember.getTeam();

            System.out.println("findTeam = " + findTeam.getName());

            // 팀수정 - fk update
            Team newTeam = em.find(Team.class, 100L);
            findMember.setTeam(newTeam);
//
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
