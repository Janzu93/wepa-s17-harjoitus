package wepa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String index() {
        return "redirect:/uutiset/sivu/0";
    }

    @GetMapping("*")
    @ResponseBody
    public String error404() {
        return "404 - Sivua ei löytynyt";
    }
}
