package study.clone.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.clone.dto.MemberDto;
import study.clone.dto.ResponseDto;
import study.clone.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberApiController {

  private final MemberService memberService;

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody @Valid final MemberDto memberDto) {
    ResponseDto responseDto = new ResponseDto();

    if (memberService.signUp(memberDto)) {
      responseDto.setSuccess(true);
      responseDto.setMessage("회원 가입에 성공하였습니다.");
    } else {
      responseDto.setSuccess(false);
      responseDto.setMessage("알 수 없는 이유로 회원 가입에 실패하였습니다.");
    }

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }
}
