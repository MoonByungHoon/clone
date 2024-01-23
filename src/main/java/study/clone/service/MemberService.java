package study.clone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.clone.dto.MemberDto;
import study.clone.entity.Member;
import study.clone.exception.DuplicateUsernameException;
import study.clone.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public boolean save(final MemberDto memberDto) {

    Member member = memberDto.toEntity(memberDto);

    if (memberRepository.existsByUsername(member.getUsername())) {
      throw new DuplicateUsernameException("해당 이메일은 이미 가입된 회원입니다.");
    } else {
      memberRepository.save(member);

      return true;
    }
  }
}
