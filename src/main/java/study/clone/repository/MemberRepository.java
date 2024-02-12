package study.clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.clone.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
  boolean existsByUsername(String username);

  Optional<Member> findByUsername(String username);
}
