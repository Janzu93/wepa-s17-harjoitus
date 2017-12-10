package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.Kirjoittaja;
import wepa.domain.Uutinen;
import wepa.repository.KirjoittajaRepository;
import wepa.repository.UutinenRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class UutinenService {

    @Autowired
    private UutinenRepository uutinenRepository;

    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;

    public List<Uutinen> findAll() {
        return uutinenRepository.findAll();
    }

    public Uutinen findOne(Long id) {
        if (uutinenRepository.existsById(id)) {
            return uutinenRepository.getOne(id);
        }
        return null;
    }

    public void assignKirjoittaja(Uutinen uutinen, String nimi) {
        if (uutinen != null) {
            Kirjoittaja kirjoittaja;
            if (kirjoittajaRepository.existsByNimi(nimi)) {
                kirjoittaja = kirjoittajaRepository.findByNimi(nimi).get(0);
            } else {
                kirjoittaja = new Kirjoittaja(nimi);
            }
            List<Kirjoittaja> uutinenKirjoittajat = uutinen.getKirjoittajat();
            List<Uutinen> kirjoittajaUutiset = kirjoittaja.getUutiset();

            uutinenKirjoittajat.add(kirjoittaja);
            kirjoittajaUutiset.add(uutinen);

            kirjoittaja.setUutiset(kirjoittajaUutiset);
            uutinen.setKirjoittajat(uutinenKirjoittajat);

            kirjoittajaRepository.save(kirjoittaja);
            uutinenRepository.save(uutinen);
        }
    }

    public void create(String otsikko, String ingressi, String sisalto, String kirjoittajat) {
        Uutinen uutinen = new Uutinen(otsikko, ingressi, sisalto, LocalDate.now());
        uutinenRepository.save(uutinen);
        // Pilkotaan parametrina saatu merkkijono kirjoittajat ja kutsutaan jokaiselle kirjoittajalle metodia assignKirjoittaja()
        Arrays.asList(kirjoittajat.split(",")).forEach(kirjoittaja -> assignKirjoittaja(uutinen, kirjoittaja));
        uutinenRepository.save(uutinen);
    }

    public void delete(Long id) {
        if (uutinenRepository.existsById(id)) {
            uutinenRepository.deleteById(id);
        }
    }

    public void edit(Long id, String otsikko, String ingressi, String sisalto) {
        if (uutinenRepository.existsById(id)) {
            Uutinen uutinen = uutinenRepository.getOne(id);
            uutinen.setOtsikko(otsikko);
            uutinen.setIngressi(ingressi);
            uutinen.setSisalto(sisalto);

            uutinenRepository.save(uutinen);
        }
    }
}
