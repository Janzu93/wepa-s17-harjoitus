package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.Kirjoittaja;
import wepa.domain.Uutinen;
import wepa.repository.KirjoittajaRepository;

import java.util.List;

@Service
public class KirjoittajaService {


    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;

    public List<Kirjoittaja> findAll() {
        return kirjoittajaRepository.findAll();
    }

    public Kirjoittaja findOne(Long id) {
        if (kirjoittajaRepository.existsById(id)) {
            return kirjoittajaRepository.getOne(id);
        }
        return null;
    }

    public void create(String nimi) {
        kirjoittajaRepository.save(new Kirjoittaja(nimi));
    }

    public void delete(Long id) {
        if (kirjoittajaRepository.existsById(id)) {
            kirjoittajaRepository.deleteById(id);
        }
    }

    public void edit(Long id, String nimi) {
        if (kirjoittajaRepository.existsById(id)) {
            Kirjoittaja kirjoittaja = kirjoittajaRepository.getOne(id);
            kirjoittaja.setNimi(nimi);
            kirjoittajaRepository.save(kirjoittaja);
        }
    }
}
