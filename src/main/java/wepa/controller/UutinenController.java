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
import wepa.domain.Kategoria;
import wepa.domain.Uutinen;
import wepa.repository.KategoriaRepository;
import wepa.repository.UutinenRepository;
import wepa.service.FileObjectService;
import wepa.service.KategoriaService;
import wepa.service.UutinenService;

import java.io.IOException;

@Controller
@Transactional
public class UutinenController {

    @Autowired
    private UutinenService uutinenService;

    @Autowired
    private UutinenRepository uutinenRepository;

    @Autowired
    private FileObjectService fileObjectService;

    @Autowired
    private KategoriaService kategoriaService;

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

    @GetMapping("/")
    public String etusivu(Model model) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        model.addAttribute("kategoriat", kategoriaService.findAll());
        model.addAttribute("uutiset", uutinenRepository.findAll(pageable));

        return "index";
    }


    /*
    Listataan uutiset
        pageable annetaan attribuuttina mallille jotta voimme varmistaa pageable.hasPrevious() metodikutsulla
        olemmeko ensimmäisellä sivulla
        sivujaJaljella metodi palauttaa totuusarvon jonka avulla tiedetään onko sivuja lisää. Templatessa näitä käytetään
        nappuloiden luomiseen tarvittaessa.
     */
    @GetMapping("/uutiset/sivu/{sivuNumero}/{sort}/{suunta}")
    public String uutinenList(Model model, @PathVariable int sivuNumero, @PathVariable String sort, @PathVariable String suunta) {
        Pageable pageable;
        if (suunta.equals("asc")) {
            pageable = PageRequest.of(sivuNumero, 5, Sort.Direction.ASC, sort);
        } else {
            pageable = PageRequest.of(sivuNumero, 5, Sort.Direction.DESC, sort);
        }
        model.addAttribute("kategoriat", kategoriaService.findAll());
        model.addAttribute("pageable", pageable);
        model.addAttribute("sivujaJaljella", (sivujaJaljella(pageable)));
        model.addAttribute("uutiset", uutinenRepository.findAll(pageable));
        model.addAttribute("sort", sort);
        model.addAttribute("suunta", suunta);
        return "uutinen/uutiset";
    }

    /*
        Uutisten tallennus pyydetäessä. Pyyntö lähetetään Servicelle joka tallettaa tiedot. Päiväystä ei tarvitse syöttää
        parametrina, koska voimme käyttää Javan tarjoamaa LocalDate.now() metodia luontipäivän listaamiseen.
        Tiedosto lähetetään fileObjectServicelle tallennettavaksi.
     */
    @PostMapping("/uutiset/uusi")
    public String luo(@RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto,
                      @RequestParam("file") MultipartFile file, @RequestParam String kirjoittajat, @RequestParam String kategoriat) throws IOException {

        if (file.getContentType().equals("image/png")) {
            fileObjectService.save(file);
            uutinenService.create(otsikko, ingressi, sisalto, kirjoittajat, kategoriat);
        }
        return "redirect:/uutiset/sivu/0/otsikko/asc";
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
        return "redirect:/uutiset/sivu/0/otsikko/asc";
    }

    /*
        Uutisten muokkaussivun luonti pyydettäessä
     */
    @GetMapping("/uutiset/{id}/muokkaa")
    public String muokkausSivu(Model model, @PathVariable Long id) {
        model.addAttribute("uutinen", uutinenService.findOne(id));
        model.addAttribute("kategoriat", kategoriaService.findAll());
        return "uutinen/muokkaaUutinen";
    }

    @GetMapping("/kategoria/{sivuNumero}/{nimi}")
    public String uutisetKategorialla(Model model, @PathVariable int sivuNumero, @PathVariable String nimi) {
        Pageable pageable = PageRequest.of(sivuNumero, 5, Sort.Direction.DESC, "id");
        model.addAttribute("kategoria", kategoriaService.findByNimi(nimi).get(0));
        model.addAttribute("kategoriat", kategoriaService.findAll());
        model.addAttribute("pageable", pageable);
        model.addAttribute("sivujaJaljella", (sivujaJaljella(pageable)));
        model.addAttribute("uutiset", kategoriaService.findUutisetByNimi(nimi));
        return "uutinen/kategorianUutiset";
    }

    /*
       Uutisten muokkaus, kuvia ei tarvitse muokata ainakaan tällä hetkellä.
     */
    @PostMapping("/uutiset/{id}/muokkaa")
    public String muokkaa(@PathVariable Long id, @RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String sisalto) {
        uutinenService.edit(id, otsikko, ingressi, sisalto);
        return "redirect:/uutiset/sivu/0/otsikko";
    }

    @GetMapping("/uutiset/{id}")
    public String uutinen(Model model, @PathVariable Long id) {
        model.addAttribute("uutinen", uutinenService.findOne(id));
        model.addAttribute("kategoriat", kategoriaService.findAll());
        return "uutinen/uutinen";
    }
}
