package study.clone.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.clone.entity.Member;
import study.clone.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    return memberRepository.findByUsername(username)
            .map(this::createUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("일치하는 회원이 없습니다."));
  }

  //  해당하는 Member의 데이터가 존재한다면 UserDetails로 객체를 만들어서 리턴한다.
  private UserDetails createUserDetails(final Member member) {
    return User.builder()
            .username(member.getUsername())
            .password(member.getPassword())
            .roles(member.getRoles().toArray(new String[0]))
            .build();
  }
}