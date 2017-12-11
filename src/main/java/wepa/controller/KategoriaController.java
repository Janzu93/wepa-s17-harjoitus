package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.service.KategoriaService;

@Controller
@Transactional
public class KategoriaController {

    @Autowired
    private KategoriaService kategoriaService;

    @GetMapping("/kategoriat")
    public String kategoriaList(Model model) {
        model.addAttribute("kategoriat", kategoriaService.findAll());

        return "kategoria/kategoriat";
    }

    @GetMapping("/kategoriat/uusi")
    public String luontiSivu() {
        return "kategoria/luoKategoria";
    }

    @GetMapping("/kategoriat/{id}/muokkaa")
    public String muokkausSivu(Model model, @PathVariable Long id) {
        model.addAttribute("kategoria", kategoriaService.findOne(id));
        return "kategoria/muokkaaKategoria";
    }

    @PostMapping("/kategoriat/{id}/muokkaa")
    public String muokkaa(@PathVariable Long id, @RequestParam String nimi) {
        kategoriaService.edit(id, nimi);

        return "redirect:/kategoriat";
    }

    @PostMapping("/kategoriat/uusi")
    public String uusiKategoria(@RequestParam String nimi) {
        kategoriaService.create(nimi);
        return "redirect:/kategoriat";
    }

    @PostMapping("/kategoriat/{id}/delete")
    public String poista(@PathVariable Long id) {
        kategoriaService.delete(id);
        return "redirect:/kategoriat";
    }
}
