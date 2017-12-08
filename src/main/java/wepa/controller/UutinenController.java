package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.domain.Uutinen;
import wepa.repository.UutinenRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class UutinenController {

    @Autowired
    private UutinenRepository uutinenRepository;

    @GetMapping("/uutiset/uusi")
    public String luontiSivu() {
        return "luoUutinen";
    }

    @GetMapping("/uutiset")
    public String uutinenList(Model model) {
        model.addAttribute("uutiset", uutinenRepository.findAll());
        return "uutiset";
    }

    @PostMapping("/uutiset/uusi")
    public String create(@RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto) {
        uutinenRepository.save(new Uutinen(otsikko, ingressi, sisalto, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        return "redirect:/";
    }
}
