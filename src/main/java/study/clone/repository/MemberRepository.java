package study.clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.clone.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
  boolean existsByUsername(String username);
}
