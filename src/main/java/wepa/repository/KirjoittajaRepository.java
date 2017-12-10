package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wepa.domain.Kirjoittaja;

import java.util.List;

@Repository
public interface KirjoittajaRepository extends JpaRepository<Kirjoittaja, Long>{

    List<Kirjoittaja> findByNimi(String nimi);

    boolean existsByNimi(String nimi);

}
