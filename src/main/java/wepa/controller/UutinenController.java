package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wepa.repository.UutinenRepository;
import wepa.service.FileObjectService;
import wepa.service.UutinenService;

import java.io.IOException;

@Controller
public class UutinenController {

    @Autowired
    private UutinenService uutinenService;

    @Autowired
    private UutinenRepository uutinenRepository;

    @Autowired
    private FileObjectService fileObjectService;

    @GetMapping("/")
    public String etusivu(Model model) {
        Pageable pageable = PageRequest.of(0,5, Sort.Direction.DESC,"julkaisupaiva");
        model.addAttribute("uutiset", uutinenRepository.findAll(pageable));
        return "index";
    }
    @GetMapping("/uutiset/uusi")
    public String luontiSivu() {
        return "uutinen/luoUutinen";
    }

    @GetMapping("/uutiset")
    public String uutinenList(Model model) {
        Pageable pageable = PageRequest.of(0,5, Sort.Direction.DESC,"julkaisupaiva");
        model.addAttribute("pageable", pageable);
        model.addAttribute("sivujaJaljella", (pageable.getPageNumber()+1) * 5 < uutinenRepository.count());
        model.addAttribute("uutiset", uutinenRepository.findAll(pageable));
        return "uutinen/uutiset";
    }

    @GetMapping("/uutiset/sivu/{sivuNumero}")
    public String uutinenList(Model model, @PathVariable int sivuNumero) {
        Pageable pageable = PageRequest.of(sivuNumero,5, Sort.Direction.DESC,"julkaisupaiva");
        model.addAttribute("pageable", pageable);
        model.addAttribute("sivujaJaljella", (pageable.getPageNumber()+1) * 5 < uutinenRepository.count());
        model.addAttribute("uutiset", uutinenRepository.findAll(pageable));
        return "uutinen/uutiset";
    }


    @PostMapping("/uutiset/uusi")
    @Transactional
    public String luo(@RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto, @RequestParam("file") MultipartFile file) throws IOException{
        fileObjectService.save(file);
        uutinenService.create(otsikko, ingressi, sisalto);
        return "redirect:/uutiset";
    }

    @PostMapping("/uutiset/{id}/delete")
    public String poista(@PathVariable Long id) {
        uutinenService.delete(id);
        fileObjectService.delete(id-1);
        return "redirect:/uutiset";
    }

    @GetMapping("/uutiset/{id}/muokkaa")
    public String muokkausSivu(Model model, @PathVariable Long id) {
        model.addAttribute("uutinen", uutinenService.findOne(id));
        return "uutinen/muokkaaUutinen";
    }

    @PostMapping("/uutiset/{id}/muokkaa")
    public String muokkaa(@PathVariable Long id, @RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto) {
        uutinenService.edit(id, otsikko, ingressi, sisalto);
        return "redirect:/uutiset";
    }
}
