package ua.sirkostya009.ui.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class ErrorControllerImpl implements ErrorController {

    @GetMapping("/error")
    public String error(Model model, HttpServletResponse response) {
        model.addAttribute("title", response.getStatus());

        return "error/" + switch (response.getStatus()) {
            case 404 -> "404";
            default -> "unknown";
        };
    }

}
