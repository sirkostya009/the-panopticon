package ua.sirkostya009.ui.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.ui.client.AuthClient;
import ua.sirkostya009.ui.client.LibraryClient;
import ua.sirkostya009.ui.client.UserClient;
import ua.sirkostya009.ui.exception.LoginException;
import ua.sirkostya009.ui.model.LoginRequest;
import ua.sirkostya009.ui.model.RegistrationRequest;
import ua.sirkostya009.ui.model.User;

import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@SessionAttributes({"jwt", "user"})
public class MainController {

    private final AuthClient authClient;
    private final UserClient userClient;
    private final LibraryClient libraryClient;

    @GetMapping
    public String home(@RequestParam(value = "page", defaultValue = "0") int page,
                       @SessionAttribute(value = "user", required = false) User user,
                       @SessionAttribute(value = "jwt", required = false) String jwt,
                       Model model) {
        decorate(model, "Home", user);

        try {
            var books = libraryClient.get(page, jwt);
            model.addAttribute("books", books.getContent());
        } catch (Exception ignored) {
        }

        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        decorate(model, "Login", null);

        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String login,
                               @RequestParam String password,
                               HttpServletRequest request) {
        if (login.isBlank())
            throw new LoginException("Login cannot be blank");

        if (password.isBlank())
            throw new LoginException("Password cannot be blank");

        String jwt;
        try {
            jwt = "Bearer " + authClient.authenticate(new LoginRequest(login, password));
        } catch (Exception e) {
            throw new LoginException("Failed to authenticate", e);
        }

        var user = userClient.self(jwt);

        request.getSession().setAttribute("jwt", jwt);
        request.getSession().setAttribute("user", user);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model) {
        decorate(model, "Register", null);

        authClient.register(new RegistrationRequest(null, null));
        return "register";
    }

    @ExceptionHandler(LoginException.class)
    public String handleException(LoginException e, Model model) {
        decorate(model, "Login", null);
        model.addAllAttributes(ModelUtils.defaults(Map.of(
                "error", e.getCause() != null ? e.getMessage() + "\nCause: " + e.getCause().getMessage() : e.getMessage()
        )));

        return "login";
    }

    private void decorate(Model model, String title, User user) {
        // this model building shit will get really out of hand as the project scales
        model.addAllAttributes(ModelUtils.defaults(
                user != null
                ? Map.of(
                        "title", title,
                        "user", user
                )
                : Map.of("title", title)
        ));
    }

}
