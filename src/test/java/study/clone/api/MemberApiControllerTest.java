package study.clone.api;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.clone.dto.AddressDto;
import study.clone.dto.MemberDto;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberApiControllerTest {

  @Autowired
  private MemberApiController memberApiController;

  @Autowired
  private EntityManager em;

  @Test
  @Commit
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
    memberDto2.setUsername("안녕하세요.");
    memberDto2.setPassword("1234");
    memberDto2.setAddressDto(addressDto2);

    memberApiController.signUp(memberDto);

    memberApiController.signUp(memberDto2);

    assertThat()
  }
}