package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String luo(@RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto) {
        uutinenRepository.save(new Uutinen(otsikko, ingressi, sisalto, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        return "redirect:/";
    }

    @DeleteMapping("/uutiset/{id}")
    public String poista(@PathVariable Long id) {
        if(uutinenRepository.exists(id)) {
            uutinenRepository.delete(id);
        }
        return "redirect:/uutiset";
    }

    @PostMapping("/uutiset/{id}")
    public String muokkaa(@PathVariable Long id, @RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto) {
        if (uutinenRepository.exists(id)) {
           Uutinen uutinen = uutinenRepository.findOne(id);
           uutinen.setOtsikko(otsikko);
           uutinen.setIngressi(ingressi);
           uutinen.setSisalto(sisalto);
        }
        return "redirect:/uutiset/" + id;
    }
}
