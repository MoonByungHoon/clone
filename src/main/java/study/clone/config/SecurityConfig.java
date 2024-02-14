package study.clone.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.clone.config.jwt.JwtAuthenticationFilter;
import study.clone.config.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
//             REST API 개발이기 때문에 basic auth 및 csrf 보안을 사용하지 않음
            .httpBasic(HttpBasicConfigurer::disable)
            .csrf(CsrfConfigurer::disable)
//             JWT를 사용할 것이기 위해서 세션 샤용을 하지 않음.
            .sessionManagement(httpSecuritySessionManagementConfigurer ->
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            스프링 시큐리티 설정.
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry
//                            해당 API에 대해서는 시큐리티가 모든 요청을 허가.
                                    .requestMatchers("/", "/auth/sign-up", "/auth/sign-in").permitAll()
//                            USER 권한이 있어야지만 요청이 가능함.
                                    .requestMatchers("/auth/test").hasRole("USER")
//                            그 밖에 모든 요청에 대해서는 인증을 요구함을 설정.
                                    .anyRequest().authenticated()
            )
//            JWT 인증을 위해서 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 이전에 먼저 실행
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class).build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
//     BCrypt Encoder 사용
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}