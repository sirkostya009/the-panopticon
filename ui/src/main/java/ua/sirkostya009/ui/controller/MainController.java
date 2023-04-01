package ua.sirkostya009.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.sirkostya009.ui.client.LibraryClient;
import ua.sirkostya009.ui.model.User;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@SessionAttributes({"jwt", "user"})
public class MainController {

    private final LibraryClient libraryClient;

    @GetMapping
    public String home(@RequestParam(value = "page", defaultValue = "0") int page,
                       @SessionAttribute(value = "user", required = false) User user,
                       @SessionAttribute(value = "jwt", required = false) String jwt,
                       Model model) {
        ModelUtils.decorate(model, "Home", user);

        try {
            var books = libraryClient.bought(page, jwt);
            model.addAttribute("books", books.getContent());
        } catch (Exception ignored) {
        }

        return "index";
    }

}
