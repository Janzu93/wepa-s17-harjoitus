package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wepa.repository.UutinenRepository;
import wepa.service.UutinenService;

@Controller
public class UutinenController {

    @Autowired
    private UutinenService uutinenService;

    @GetMapping("/uutiset/uusi")
    public String luontiSivu() {
        return "luoUutinen";
    }

    @GetMapping("/uutiset")
    public String uutinenList(Model model) {
        model.addAttribute("uutiset", uutinenService.findAll());
        return "uutiset";
    }

    @PostMapping("/uutiset/uusi")
    public String luo(@RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto) {
        uutinenService.create(otsikko, ingressi, sisalto);
        return "redirect:/";
    }

    @PostMapping("/uutiset/{id}/delete")
    public String poista(@PathVariable Long id) {
        uutinenService.delete(id);
        return "redirect:/uutiset";
    }

    @PostMapping("/uutiset/{id}")
    public String muokkaa(@PathVariable Long id, @RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto) {
        uutinenService.edit(id, otsikko, ingressi, sisalto);
        return "redirect:/uutiset/" + id;
    }
}
