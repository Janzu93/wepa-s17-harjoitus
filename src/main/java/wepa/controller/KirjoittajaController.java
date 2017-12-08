package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.domain.Kirjoittaja;
import wepa.repository.KirjoittajaRepository;

import java.util.ArrayList;

@Controller
public class KirjoittajaController {

    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;

    @GetMapping("/kirjoittajat")
    public String kirjoittajatList(Model model) {
        model.addAttribute("kirjoittajat", kirjoittajaRepository.findAll());
        return "kirjoittajat";
    }

    @GetMapping("kirjoittajat/uusi")
    public String luontiSivu() {
        return "luoKirjoittaja";
    }

    @PostMapping("/kirjoittajat/uusi")
    public String create(@RequestParam String nimi) {
        kirjoittajaRepository.save(new Kirjoittaja(nimi, new ArrayList<>()));
        return "redirect:/";
    }
}
