package ua.sirkostya009.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.ui.client.AuthClient;
import ua.sirkostya009.ui.model.RegistrationRequest;
import ua.sirkostya009.ui.model.User;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final AuthClient client;

    @GetMapping
    public String register(Model model) {
        ModelUtils.decorate(model, "Register", null);

        client.register(new RegistrationRequest(null, null));
        return "register";
    }

    @PostMapping
    public String register(@RequestBody User user, Model model) {
        throw new UnsupportedOperationException("REQUIRES IMPLEMENTATION");
    }

}
