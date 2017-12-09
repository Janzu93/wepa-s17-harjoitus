package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.Kategoria;
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
        if (kategoriaRepository.exists(id)) {
            kategoriaRepository.delete(id);
        }
    }

    public void edit(Long id, String nimi) {
        if (kategoriaRepository.exists(id)) {
            Kategoria kategoria = kategoriaRepository.findOne(id);
            kategoria.setNimi(nimi);
            kategoriaRepository.save(kategoria);
        }
    }

    public List<Kategoria> findAll() {
        return kategoriaRepository.findAll();
    }

    public Kategoria findOne(Long id) {
        if (kategoriaRepository.exists(id)) {
            return kategoriaRepository.findOne(id);
        }
        return null;
    }
}
