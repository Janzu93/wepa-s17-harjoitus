package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.domain.Artikkeli;
import wepa.repository.ArtikkeliRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ArtikkeliController {

    @Autowired
    private ArtikkeliRepository artikkeliRepository;

    @GetMapping("/uutiset/uusi")
    public String luontiSivu() {
        return "luoArtikkeli";
    }

    @GetMapping("/uutiset")
    public String uutisetLista(Model model) {
        model.addAttribute("uutiset", artikkeliRepository.findAll());
        return "uutiset";
    }

    @PostMapping("/uutiset/uusi")
    public String create(@RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto) {
        artikkeliRepository.save(new Artikkeli(otsikko, ingressi, sisalto, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        return "redirect:/";
    }
}
