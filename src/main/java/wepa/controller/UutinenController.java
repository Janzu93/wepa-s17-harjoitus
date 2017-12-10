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

    /*
    Tätä metodia kutsutaan kun tarvitaan tieto ollaanko viimeisellä sivulla.
    Algoritmi toimii niin että pageablelta pyydettyyn sivunumeroon (0-indeksöity) lisätään 1
    jotta saadaan aito sivunumero, tämä kerrottaessa sivunkoolla (getPageSize()) saadaan luku
    jonka vastatessa uutistenmäärää (uutinenRepository.count()) tiedetään että sivuja ei enää ole
    */
    public boolean sivujaJaljella(Pageable pageable) {
        return (pageable.getPageNumber() + 1) * pageable.getPageSize() < uutinenRepository.count();
    }

    //Uuden uutisen luontisivu luodaan pyydettäessä
    @GetMapping("/uutiset/uusi")
    public String luontiSivu() {
        return "uutinen/luoUutinen";
    }

    /*
    Listataan uutiset
    pageable annetaan attribuuttina mallille jotta voimme varmistaa pageable.hasPrevious() metodikutsulla
    olemmeko ensimmäisellä sivulla
    sivujaJaljella metodi palauttaa totuusarvon jonka avulla tiedetään onko sivuja lisää. Templatessa näitä käytetään
    nappuloiden luomiseen tarvittaessa.
     */
    @GetMapping("/uutiset/sivu/{sivuNumero}/{sort}")
    public String uutinenList(Model model, @PathVariable int sivuNumero, @PathVariable String sort) {
        Pageable pageable = PageRequest.of(sivuNumero, 5, Sort.Direction.DESC, sort);
        model.addAttribute("pageable", pageable);
        model.addAttribute("sivujaJaljella", (sivujaJaljella(pageable)));
        model.addAttribute("uutiset", uutinenRepository.findAll(pageable));
        return "uutinen/uutiset";
    }

    /*
    Uutisten tallennus pyydetäessä. Pyyntö lähetetään Servicelle joka tallettaa tiedot. Päiväystä ei tarvitse syöttää
    parametrina, koska voimme käyttää Javan tarjoamaa LocalDate.now() metodia luontipäivän listaamiseen.
    Tiedosto lähetetään fileObjectServicelle tallennettavaksi.
     */
    @PostMapping("/uutiset/uusi")
    @Transactional
    public String luo(@RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto, @RequestParam("file") MultipartFile file, @RequestParam String kirjoittajat) throws IOException {
        fileObjectService.save(file);
        uutinenService.create(otsikko, ingressi, sisalto, kirjoittajat);
        return "redirect:/uutiset/sivu/0/otsikko";
    }

    /*
    Uutinen ja sen kuva poistetaan. Spring 2 omituisuuksista johtuen fileObjectilla ja Uutisella on jostain syystä
    yhteinen juokseva ID numerointi, tämän vuoksi service poistaa id-1.
     */
    @PostMapping("/uutiset/{id}/delete")
    @Transactional
    public String poista(@PathVariable Long id) {
        uutinenService.delete(id);
        fileObjectService.delete(id - 1);
        return "redirect:/uutiset/sivu/0/otsikko";
    }

    /*
    Uutisten muokkaussivun luonti pyydettäessä
     */
    @GetMapping("/uutiset/{id}/muokkaa")
    public String muokkausSivu(Model model, @PathVariable Long id) {
        model.addAttribute("uutinen", uutinenService.findOne(id));
        return "uutinen/muokkaaUutinen";
    }

    /*
        Uutisten muokkaus, kuvia ei tarvitse muokata ainakaan tällä hetkellä.
     */
    @PostMapping("/uutiset/{id}/muokkaa")
    public String muokkaa(@PathVariable Long id, @RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto) {
        uutinenService.edit(id, otsikko, ingressi, sisalto);
        return "redirect:/uutiset/sivu/0/otsikko";
    }
}
