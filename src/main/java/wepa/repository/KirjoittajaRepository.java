package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wepa.domain.Kirjoittaja;

@Repository
public interface KirjoittajaRepository extends JpaRepository<Kirjoittaja, Long>{
}
