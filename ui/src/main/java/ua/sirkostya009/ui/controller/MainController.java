package ua.sirkostya009.ui.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.sirkostya009.ui.client.LibraryClient;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@SessionAttributes("jwt")
public class MainController {

    private final LibraryClient client;

    @GetMapping
    public String home(@RequestParam(value = "page", defaultValue = "0") int page,
                       @SessionAttribute(value = "jwt", required = false) String jwt,
                       Model model) {
        try {
            var books = client.bought(page, jwt);
            model.addAttribute("books", books.getContent());
        } catch (Exception e) {
            log.error("Exception thrown in GET-home", e);
        }

        return "index";
    }

    @GetMapping("/catalog")
    public String catalog(@RequestParam(value = "page", defaultValue = "0") int page,
                          @SessionAttribute(value = "jwt", required = false) String jwt,
                          Model model) {
        if (jwt == null)
            return "redirect:/login";

        var books = client.get(page, jwt);

        model.addAttribute("books", books);

        return "catalog";
    }

    @GetMapping("/self")
    public String self(@SessionAttribute(value = "jwt", required = false) String jwt) {
        if (jwt == null)
            return "redirect:/login";

        return "self";
    }

}
