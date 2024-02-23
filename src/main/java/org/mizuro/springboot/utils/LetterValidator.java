package org.mizuro.springboot.utils;

import lombok.AllArgsConstructor;
import org.mizuro.springboot.entity.LetterEntity;
import org.mizuro.springboot.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class LetterValidator implements Validator {

    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return LetterEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LetterEntity letterEntity = (LetterEntity) target;
        if (letterEntity.getRecipient().getEmail().equals(userService.getCurrentUserEmail()))
            errors.rejectValue("recipient", "", "You can't send letter to yourself");
    }
}
