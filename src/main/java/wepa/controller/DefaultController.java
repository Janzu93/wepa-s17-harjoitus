package wepa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Transactional
public class DefaultController {

    @GetMapping("*")
    @ResponseBody
    public String error404() {
        return "404 - Sivua ei löytynyt";
    }
}
