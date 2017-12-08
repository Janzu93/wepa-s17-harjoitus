package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String luo(@RequestParam String nimi) {
        kirjoittajaRepository.save(new Kirjoittaja(nimi, new ArrayList<>()));
        return "redirect:/";
    }

    @PostMapping("/kirjoittajat/{id}/delete")
    public String poista(@PathVariable Long id) {
        if (kirjoittajaRepository.exists(id)) {
            kirjoittajaRepository.delete(id);
        }
        return "redirect:/kirjoittajat";
    }

    @PostMapping("kirjoittajat/{id}")
    public String muokkaa(@PathVariable Long id, @RequestParam String nimi) {
        if (kirjoittajaRepository.exists(id)) {
            Kirjoittaja k = kirjoittajaRepository.findOne(id);

            k.setNimi(nimi);
            kirjoittajaRepository.save(k);

            return "redirect:/kirjoittajat/" + id;
        }
        return "redirect:/kirjoittajat";
    }
}
