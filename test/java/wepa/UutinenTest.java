package wepa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Uutinen;
import wepa.repository.UutinenRepository;
import wepa.service.UutinenService;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UutinenTest {

    @Autowired
    private UutinenRepository uutinenRepository;

    @Autowired
    private UutinenService uutinenService;

    @Test
    public void uutinenCreatesUutinen() {
        uutinenService.create("otsikko", "ingressi", "sisalto", "kirjoittajat", "kategoriat");

        Assert.assertTrue(uutinenRepository.count() > 0);

    }

    @Test
    public void uutinenHasAllParams() {
        uutinenService.create("otsikko", "ingressi", "sisalto", "kirjoittajat", "kategoriat");

        Uutinen uutinen = uutinenService.findAll().get(0);

        Assert.assertTrue(!uutinen.getKirjoittajat().contains("kirjoittajat"));
        Assert.assertTrue(!uutinen.getKategoriat().contains("kategoriat"));
        Assert.assertTrue(uutinen.getIngressi().equals("otsikko"));
        Assert.assertTrue(uutinen.getOtsikko().equals("otsikko"));
        Assert.assertTrue(uutinen.getSisalto().equals("sisalto"));
        Assert.assertTrue(uutinen.getJulkaisupaiva().equals(LocalDate.now()));
    }

    @Test
    public void uutinenDeletesUutinen() {
        uutinenService.create("otsikko", "ingressi", "sisalto", "kirjoittajat", "kategoriat");

        uutinenService.delete(uutinenRepository.findAll().get(0).getId());
        Assert.assertTrue(uutinenRepository.count() == 0);
    }
}
