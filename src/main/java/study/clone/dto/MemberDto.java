package study.clone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import study.clone.entity.Address;
import study.clone.entity.Member;
import study.clone.entity.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

  private Long id;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  //  @NotBlank
  private AddressDto addressDto;

  private List<OrderDto> orderDtos = new ArrayList<>();

//  private Set<Role> roles;

  private Role role;

  public Member toEntity(final MemberDto memberDto) {
    return Member.builder()
            .username(this.username)
            .password(this.password)
            .address(
                    memberDto.getAddressDto() != null ?
                            Address.builder()
                                    .city(this.addressDto.getCity())
                                    .street(this.addressDto.getStreet())
                                    .zipcode(this.addressDto.getZipcode())
                                    .build() : new Address()
            )
            .role(this.role)
            .build();
  }
}
