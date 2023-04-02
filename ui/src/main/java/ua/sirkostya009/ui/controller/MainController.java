package ua.sirkostya009.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.sirkostya009.ui.client.LibraryClient;
import ua.sirkostya009.ui.model.User;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@SessionAttributes({"jwt", "user"})
public class MainController {

    private final LibraryClient client;

    @GetMapping
    public String home(@RequestParam(value = "page", defaultValue = "0") int page,
                       @SessionAttribute(value = "user", required = false) User user,
                       @SessionAttribute(value = "jwt", required = false) String jwt,
                       Model model) {
        try {
            var books = client.bought(page, jwt);
            model.addAttribute("books", books.getContent());
        } catch (Exception ignored) {
        }

        return "index";
    }

    @GetMapping("/catalog")
    public String catalog(@RequestParam(value = "page", defaultValue = "0") int page,
                          @SessionAttribute(value = "user", required = false) User user,
                          @SessionAttribute(value = "jwt", required = false) String jwt,
                          Model model) {
        if (user == null)
            return "redirect:/login";

        var books = client.get(page, jwt);

        model.addAttribute("books", books);

        return "catalog";
    }

    @GetMapping("/self")
    public String self(@SessionAttribute(value = "user", required = false) User user,
                       Model model) {
        if (user == null)
            return "redirect:/login";

        return "self";
    }

}
