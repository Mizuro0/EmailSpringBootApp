package org.mizuro.springboot.security;

import lombok.AllArgsConstructor;
import org.mizuro.springboot.entity.UserEntity;
import org.mizuro.springboot.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException
                        ("User with email '" + username + "' not found"));
        return UserDetailsImpl.build(userEntity);
    }
}
