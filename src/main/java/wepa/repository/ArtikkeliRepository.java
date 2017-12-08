package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.Artikkeli;

public interface ArtikkeliRepository extends JpaRepository<Artikkeli, Long>{

}
