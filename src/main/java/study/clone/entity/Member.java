package study.clone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.clone.entity.time.BaseEntity;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Member extends BaseEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "member_id")
  private Long id;

  @Column(length = 40, nullable = false)
  private String username;

  @Column(length = 256, nullable = false)
  private String password;

  @Embedded
  private Address address;

  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>(); //주문

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Set<Role> roles = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
