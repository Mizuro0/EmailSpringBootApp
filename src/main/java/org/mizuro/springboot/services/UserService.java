package org.mizuro.springboot.services;

import lombok.RequiredArgsConstructor;
import org.mizuro.springboot.entity.UserEntity;
import org.mizuro.springboot.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepo userRepo;
    private BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getCurrentUserEmail() {
        return getAuthentication().getName();
    }

    public int getCurrentUserId() {
        return Objects.requireNonNull(userRepo.findByEmail(getCurrentUserEmail()).orElse(null)).getId();
    }

    @Transactional
    public void save(UserEntity userEntity) {
        userEntity.setPassword(encoder().encode(userEntity.getPassword()));
        userEntity.setCreatedAt(new Date());
        userRepo.save(userEntity);
    }

    public UserEntity findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}