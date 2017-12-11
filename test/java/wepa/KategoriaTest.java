package wepa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Kategoria;
import wepa.repository.KategoriaRepository;
import wepa.service.KategoriaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class KategoriaTest {

    @Autowired
    private KategoriaRepository kategoriaRepository;

    @Autowired
    private KategoriaService kategoriaService;

    @Test
    public void kategoriaCreatesKategoria() {

        kategoriaService.create("nimi");
        Assert.assertTrue(kategoriaRepository.count() > 0);
    }

    @Test
    public void kategoriaHasAttributes() {
        Kategoria kategoria = new Kategoria("nimi");

        kategoriaService.create(kategoria.getNimi());

        Assert.assertTrue(!kategoriaRepository.findByNimi("nimi").isEmpty());
    }

    @Test
    public void kategoriaDeletesKategoria() {

        kategoriaService.create("nimi");

        Kategoria kategoria = kategoriaRepository.findAll().get(0);
        kategoriaService.delete(kategoria.getId());

        Assert.assertTrue(kategoriaRepository.count() == 0);
    }
}
