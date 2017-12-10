package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.Uutinen;
import wepa.repository.UutinenRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class UutinenService {

    @Autowired
    private UutinenRepository uutinenRepository;

    public List<Uutinen> findAll() {
        return uutinenRepository.findAll();
    }

    public Uutinen findOne(Long id) {
        if (uutinenRepository.existsById(id)) {
            return uutinenRepository.getOne(id);
        }
        return null;
    }

    public void create(String otsikko, String ingressi, String sisalto) {
        uutinenRepository.save(new Uutinen(otsikko, ingressi, sisalto, LocalDate.now()));
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
