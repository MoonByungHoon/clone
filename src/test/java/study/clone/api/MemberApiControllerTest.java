package study.clone.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import study.clone.dto.AddressDto;
import study.clone.dto.MemberDto;
import study.clone.entity.Member;
import study.clone.entity.Role;
import study.clone.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
class MemberApiControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MemberApiController memberApiController;

  @Autowired
  private EntityManager em;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void 더미데이터생성() {
    memberRepository.save(new Member(
            1L,
            "안녕하세요.",
            passwordEncoder.encode("1234"),
            null,
            List.of(),
            Role.USER)
    );
  }

  @Test
  void signin_Success() throws Exception {
    // given
    MemberDto request = new MemberDto();
    request.setUsername("안녕하세요.");
    request.setPassword("1234");
    request.setRole(Role.USER);

    // when
    var result = mockMvc.perform(post("/auth/sign-in")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

    // then
    result.andExpect(status().isOk());
  }

  @Test
  void 회원가입() {
    AddressDto addressDto = new AddressDto();
    addressDto.setCity("인천");
    addressDto.setStreet("서구");
    addressDto.setZipcode("신현동");

    MemberDto memberDto = new MemberDto();
    memberDto.setUsername("안녕하세요.");
    memberDto.setPassword("1234");
    memberDto.setAddressDto(addressDto);

    AddressDto addressDto2 = new AddressDto();
    addressDto2.setCity("서울");
    addressDto2.setStreet("관악구");
    addressDto2.setZipcode("신림동");

    MemberDto memberDto2 = new MemberDto();
    memberDto2.setUsername("하이");
    memberDto2.setPassword("1234");
    memberDto2.setAddressDto(addressDto2);

    ResponseEntity<?> responseEntity = memberApiController.signUp(memberDto);
    ResponseEntity<?> responseEntity1 = memberApiController.signUp(memberDto2);

    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity1).isNotNull();
  }

  @Test
  void 로그인및토큰인증() {

  }
}