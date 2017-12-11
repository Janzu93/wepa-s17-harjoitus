package wepa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Kirjoittaja;
import wepa.repository.KirjoittajaRepository;
import wepa.service.KirjoittajaService;

import javax.validation.constraints.AssertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class KirjottajaTest {

    @Autowired
    private KirjoittajaService kirjoittajaService;

    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;

    @Test
    public void KirjoittajaCreatesKirjoittaja() {
        kirjoittajaService.create("nimi");

        Assert.assertTrue(kirjoittajaRepository.count() > 0);
    }

    @Test
    public void KirjoittajaHasAttributes() {
        kirjoittajaService.create("nimi");

        Kirjoittaja kirjoittaja = kirjoittajaRepository.findAll().get(0);

        Assert.assertTrue(kirjoittaja.getNimi().equals("nimi"));
        Assert.assertTrue(kirjoittaja.getUutiset() != null);
    }

    @Test
    public void KirjoittajaDeletesKirjoittaja() {
        kirjoittajaService.create("nimi");

        Kirjoittaja kirjoittaja = kirjoittajaRepository.findAll().get(0);

        kirjoittajaService.delete(kirjoittaja.getId());

        Assert.assertTrue(kirjoittajaRepository.count() == 0);
    }
}
