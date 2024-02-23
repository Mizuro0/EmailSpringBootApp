package org.mizuro.springboot.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mizuro.springboot.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private int id;
    private String password;
    private String surname;
    private String name;
    private String email;
    private Date birthDate;
    private Date createdAt;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(UserEntity userEntity) {
        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(userEntity.getERole().name()));
        return new UserDetailsImpl(
                userEntity.getId(),
                userEntity.getPassword(),
                userEntity.getSurname(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getBirthDate(),
                userEntity.getCreatedAt(),
                authorityList
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
