package ua.sirkostya009.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.sirkostya009.ui.client.AuthClient;
import ua.sirkostya009.ui.exception.ValidationException;
import ua.sirkostya009.ui.model.RegistrationRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final AuthClient client;

    @GetMapping
    public String register() {
        return "register";
    }

    @PostMapping
    public String register(RegistrationRequest registrationRequest) {
        registrationRequest.validate();
        client.register(registrationRequest);

        return "redirect:/";
    }

    @ExceptionHandler(ValidationException.class)
    public String handleIllegalArgumentException(ValidationException e, Model model) {
        model.addAttribute("error", e.getMessage());

        var registrationRequest = (RegistrationRequest) e.getObject();
        model.addAttribute("firstName", registrationRequest.firstName());
        model.addAttribute("lastName", registrationRequest.lastName());
        model.addAttribute("username", registrationRequest.username());
        model.addAttribute("email", registrationRequest.email());
        model.addAttribute("password", registrationRequest.password());
        model.addAttribute("confirmPassword", registrationRequest.confirmPassword());

        return register();
    }
}
