package org.mizuro.springboot.utils;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import org.mizuro.springboot.entity.UserEntity;
import org.mizuro.springboot.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        UserEntity userEntity = (UserEntity) target;
        if(userService.findByEmail(userEntity.getEmail()) != null)
            errors.rejectValue("email", "", "This email is already token");
    }
}
