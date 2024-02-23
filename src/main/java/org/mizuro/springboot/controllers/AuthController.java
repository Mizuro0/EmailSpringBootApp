package org.mizuro.springboot.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mizuro.springboot.entity.UserEntity;
import org.mizuro.springboot.services.UserService;
import org.mizuro.springboot.utils.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserValidator userValidator;
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userEntity") @Valid UserEntity userEntity,
                               BindingResult bindingResult) {
        userValidator.validate(userEntity, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        userService.save(userEntity);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
