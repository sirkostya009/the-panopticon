package ua.sirkostya009.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.sirkostya009.ui.client.LibraryClient;
import ua.sirkostya009.ui.model.Book;
import ua.sirkostya009.ui.model.BookWithPage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
@SessionAttributes("jwt")
public class BookController {
    private final LibraryClient client;

    @GetMapping("/{id}")
    public String book(@PathVariable String id,
                       @SessionAttribute("jwt") String jwt,
                       Model model) {
        var book = Book.from(client.id(id, null, jwt));

        model.addAttribute("book", book);
        model.addAttribute("title", book.title());

        return "book";
    }

    @GetMapping("/{id}/{page}")
    public String book(@PathVariable String id,
                       @PathVariable int page,
                       @SessionAttribute("jwt") String jwt,
                       Model model) {
        var bookWithPage = BookWithPage.from(client.id(id, page, jwt));

        model.addAttribute("page", bookWithPage);
        model.addAttribute("title", bookWithPage.title());

        return "book";
    }
}
