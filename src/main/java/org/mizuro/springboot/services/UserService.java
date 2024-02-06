package org.mizuro.springboot.services;

import lombok.AllArgsConstructor;
import org.mizuro.springboot.repo.UserRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;

}
