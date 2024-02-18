package study.clone.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.clone.config.old.jwt.JwtAuthenticationFilter;
import study.clone.config.old.jwt.JwtTokenProvider;

/**
 * 기존에는 WebSecurityConfigurerAdapter를 extends해야했으나, 디자인에 결함이 있어서
 * 이를 해결할 수 없는 문제가 있었음.
 * Bean으로 등록하여서 SpringBoot에서 사용할 수 없는 객체를 생성하는 문제가 발생.
 * 이로 인해서 개발자가 만든 코드를 Bean으로 등록하기 어렵게 만드는 문제를 야기 시킴.
 * 이를 해결하기 위해서 @Configuration 어노테이션을 이용하여 해결함.
 * <p>
 * 기존의 WebSecurityConfigurerAdapter는 코드가 직관적이지 못했으며, 부담으 가중되어왔음.
 **/
@Configuration
@EnableWebSecurity //시큐리티 활성화. 기본 스프링의 필터 체인에 등록함.
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;

  //    2022년 패치된 @Configuration으로 인해서 기존에 복잡하고 직관적이지 못했던 코드와
//    Bean으로 등록이 어려웠던 부분이 한번에 해결됨. 이로 인해서 @Bean으로 Spring에 등록이 가능함.
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
//            REST API 개발이기 때문에
//            기본 보안 설정인 basic auth, csrf, formLogin을 사용하지 않음.
            .httpBasic(HttpBasicConfigurer::disable)
            .csrf(CsrfConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
//            JWT를 사용할 것이기 때문에 세션에 대한 사용 여부를 STATELESS로 설정.
            .sessionManagement(httpSecuritySessionManagementConfigurer ->
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            Spring Security에 대한 권한 설정.
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry
                                    .requestMatchers("/auth/test").hasRole("USER")
//                            그 밖의 모든 요총에 대해서는 인증을 허용함.
//                            .permitAll()이 아닌 .authenticated()는 반대로 인증 요청함.
                                    .anyRequest().permitAll()
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
