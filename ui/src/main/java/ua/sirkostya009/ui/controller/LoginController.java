package ua.sirkostya009.ui.controller;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.sirkostya009.ui.client.AuthClient;
import ua.sirkostya009.ui.client.UserClient;
import ua.sirkostya009.ui.model.LoginRequest;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthClient authClient;
    private final UserClient userClient;

    @GetMapping
    public String login() {
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
        } catch (FeignException.Unauthorized e) {
            throw new IllegalArgumentException("Login or password is incorrect");
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
        model.addAttribute(
                "error",
                e.getCause() != null ? e.getMessage() + "\nCause: " + e.getCause().getMessage() : e.getMessage())
        ;

        return "login";
    }

}
