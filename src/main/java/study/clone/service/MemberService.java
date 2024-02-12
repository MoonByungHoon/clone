package study.clone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.clone.config.jwt.JwtToken;
import study.clone.config.jwt.JwtTokenProvider;
import study.clone.dto.MemberDto;
import study.clone.entity.Member;
import study.clone.exception.DuplicateUsernameException;
import study.clone.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public boolean signUp(final MemberDto memberDto) {

    if (memberRepository.existsByUsername(memberDto.getUsername())) {

      throw new DuplicateUsernameException("해당 이메일은 이미 가입된 회원입니다.");

    } else {

      memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

      Member member = memberDto.toEntity(memberDto);

      memberRepository.save(member);

      return true;
    }
  }

  @Transactional
  public JwtToken signIn(final MemberDto memberDto) {

//    입력받은 회원 이름과 패스워드를 기반으로 Authentication 객체를 생성.
//    이때에 생성된 Authentication의 인증여부를 확인하는 authenticated의 값은 false이다.
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(memberDto.getUsername(), memberDto.getPassword());

//    실제 검증 절차. authenticate()를 통해서 요청된 Member에 대한 검증을 실시함.
//    authenticate 메소드가 실행될 때에 CustomUserDetailsService에서 만들어둔 loadUserByUsername가 실행된다.
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

//    인증 정보를 기반으로 JWT를 생성함.
    JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

    return jwtToken;
  }
}
