package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.repository.KirjoittajaRepository;
import wepa.service.KirjoittajaService;

@Controller
public class KirjoittajaController {

    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;

    @Autowired
    private KirjoittajaService kirjoittajaService;

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
    public String luo(@RequestParam String nimi) {
        kirjoittajaService.create(nimi);
        return "redirect:/";
    }

    @PostMapping("/kirjoittajat/{id}/delete")
    public String poista(@PathVariable Long id) {
        kirjoittajaService.delete(id);
        return "redirect:/kirjoittajat";
    }

    @PostMapping("kirjoittajat/{id}")
    public String muokkaa(@PathVariable Long id, @RequestParam String nimi) {
        kirjoittajaService.edit(id, nimi);
        return "redirect:/kirjoittajat";
    }
}
