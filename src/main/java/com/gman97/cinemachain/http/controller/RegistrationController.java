package com.gman97.cinemachain.http.controller;

import com.gman97.cinemachain.dto.UserCreateDto;
import com.gman97.cinemachain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String registerPage(Model model, @ModelAttribute("user") UserCreateDto user) {
        model.addAttribute("user", user);
        return "user/registration";
    }

    @PostMapping
    public String register(@Valid UserCreateDto userCreateDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            redirectAttributes.addFlashAttribute("user", userCreateDto);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/register";
        }

        try {
            userService.create(userCreateDto);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("user", userCreateDto);
            redirectAttributes.addFlashAttribute("errors", List.of("Пользователь с таким именем уже существует!"));
            return "redirect:/register";
        }
        return "redirect:/login";
    }
}
