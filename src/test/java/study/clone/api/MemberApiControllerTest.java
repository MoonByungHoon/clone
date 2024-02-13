package study.clone.api;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import study.clone.dto.AddressDto;
import study.clone.dto.MemberDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberApiControllerTest {

  @Autowired
  private MemberApiController memberApiController;

  @Autowired
  private EntityManager em;

  @BeforeEach
  public void 더미데이터생성() {

  }

  @Test
  public void 회원가입() {
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
  public 로그인및토큰인증() {

  }
}