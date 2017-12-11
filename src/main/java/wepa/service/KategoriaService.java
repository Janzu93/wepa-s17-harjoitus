package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.Kategoria;
import wepa.domain.Uutinen;
import wepa.repository.KategoriaRepository;

import java.util.List;

@Service
public class KategoriaService {

    @Autowired
    private KategoriaRepository kategoriaRepository;

    public void create(String nimi) {
        kategoriaRepository.save(new Kategoria(nimi));
    }

    public void delete(Long id) {
        if (kategoriaRepository.existsById(id)) {
            kategoriaRepository.deleteById(id);
        }
    }

    public void edit(Long id, String nimi) {
        if (kategoriaRepository.existsById(id)) {
            Kategoria kategoria = kategoriaRepository.getOne(id);
            kategoria.setNimi(nimi);
            kategoriaRepository.save(kategoria);
        }
    }

    public List<Kategoria> findAll() {
        return kategoriaRepository.findAll();
    }

    public Kategoria findOne(Long id) {
        if (kategoriaRepository.existsById(id)) {
            return kategoriaRepository.getOne(id);
        }
        return null;
    }

    public List<Kategoria> findByNimi(String nimi) {
        return kategoriaRepository.findByNimi(nimi);
    }

    public List<Uutinen> findUutisetByNimi(String nimi) {
        return kategoriaRepository.findByNimi(nimi).get(0).getUutiset();
    }
}
