package org.mizuro.springboot.controllers;

import jakarta.validation.Valid;
import org.mizuro.springboot.entity.LetterEntity;
import org.mizuro.springboot.entity.UserEntity;
import org.mizuro.springboot.services.LetterService;
import org.mizuro.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class EmailController {

    private final LetterService letterService;
    private final UserService userService;
    @Autowired
    private EmailController(LetterService letterService, UserService userService) {
        this.letterService = letterService;
        this.userService = userService;
    }

    @GetMapping("/inbox")
    public String inbox(Model model, @RequestParam(value = "sortBy", defaultValue = "sender") String sortBy) {
        String currentUserEmail = userService.getCurrentUserEmail();
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("letters", letterService.findInbox(userService.findByEmail(currentUserEmail), sortBy));
        return "email/inbox";
    }

    @GetMapping("/sent")
    public String sent(Model model, @RequestParam(value = "sortBy", defaultValue = "recipient") String sortBy) {
        String currentUserEmail = userService.getCurrentUserEmail();
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("letters", letterService.findSent(userService.findByEmail(currentUserEmail), sortBy));
        return "email/sent";
    }



    @GetMapping("letter/{id}")
    public String show(@PathVariable("id") int id, Model letterModel, Model userModel) {
        String currentUserEmail = userService.getCurrentUserEmail();
        UserEntity currentUser = userService.findByEmail(currentUserEmail);

        LetterEntity letter = letterService.findOne(id);

        if (letter == null ||
                !currentUser.getEmail().equals(letter.getSender().getEmail()) &&
                        !currentUser.getEmail().equals(letter.getRecipient().getEmail())) {
            return "error/letter_not_found";
        }

        letterModel.addAttribute("letter", letter);
        userModel.addAttribute("user", currentUser);

        return "email/show";
    }



    @GetMapping("/new")
    public String newLetter(Model model) {
        String currentUserEmail = userService.getCurrentUserEmail();
        UserEntity currentUser = userService.findByEmail(currentUserEmail);
        LetterEntity letter = new LetterEntity();
        letter.setSender(userService.findByEmail(currentUser.getEmail()));
        model.addAttribute("letter", letter);
        return "email/new";
    }

    @PostMapping("/sendMessage")
    public String sendLetter(@ModelAttribute("letter") @Valid LetterEntity letter, Model model,
                             BindingResult bindingResult) {

        String currentUserEmail = userService.getCurrentUserEmail();
        UserEntity sender = userService.findByEmail(currentUserEmail);

        UserEntity recipient = userService.findByEmail(letter.getRecipient().getEmail());
        if (recipient == null) {
            bindingResult.rejectValue("recipient", "error.recipient", "Пользователь с таким email не существует");
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "email/new";
        }

        letter.setSender(sender);

        letter.setRecipient(userService.findByEmail(letter.getRecipient().getEmail()));
        letterService.save(letter);

        return "redirect:/sent";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("letter", letterService.findOne(id));
        return "email/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("letter") @Valid LetterEntity letter, Model model,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
        }
        letterService.update(id, letter);
        return "redirect:/letter/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        letterService.remove(id);
        return "redirect:/inbox";
    }

}
