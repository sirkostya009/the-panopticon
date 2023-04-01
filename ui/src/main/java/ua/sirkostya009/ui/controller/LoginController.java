package ua.sirkostya009.ui.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.ui.client.AuthClient;
import ua.sirkostya009.ui.client.UserClient;
import ua.sirkostya009.ui.model.LoginRequest;

import java.util.Map;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthClient authClient;
    private final UserClient userClient;

    @GetMapping
    public String login(Model model) {
        ModelUtils.decorate(model, "Login", null);

        return "login";
    }

    @PostMapping
    public String authenticate(@RequestParam String login,
                               @RequestParam String password,
                               HttpServletRequest request) {
        if (login.isBlank())
            throw new IllegalArgumentException("Login cannot be blank");

        if (password.isBlank())
            throw new IllegalArgumentException("Password cannot be blank");

        String jwt;
        try {
            jwt = "Bearer " + authClient.authenticate(new LoginRequest(login, password));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to authenticate", e);
        }

        var user = userClient.self(jwt);

        request.getSession().setAttribute("jwt", jwt);
        request.getSession().setAttribute("user", user);

        return "redirect:/";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(IllegalArgumentException e, Model model) {
        ModelUtils.decorate(model, "Login", null);
        model.addAllAttributes(ModelUtils.defaults(Map.of(
                "error", e.getCause() != null ? e.getMessage() + "\nCause: " + e.getCause().getMessage() : e.getMessage()
        )));

        return "login";
    }

}
