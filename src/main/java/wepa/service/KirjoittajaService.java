package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.Kirjoittaja;
import wepa.repository.KirjoittajaRepository;

import java.util.List;

@Service
public class KirjoittajaService {


    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;

    public List<Kirjoittaja> findAll() {
        return kirjoittajaRepository.findAll();
    }

    public void create(String nimi) {
        kirjoittajaRepository.save(new Kirjoittaja(nimi));
    }

    public void delete(Long id) {
        if (kirjoittajaRepository.exists(id)) {
            kirjoittajaRepository.delete(id);
        }
    }

    public void edit(Long id, String nimi) {
        if (kirjoittajaRepository.exists(id)) {
            Kirjoittaja kirjoittaja = kirjoittajaRepository.findOne(id);
            kirjoittaja.setNimi(nimi);
        }
    }
}
