package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wepa.domain.Kategoria;

import java.util.List;

@Repository
public interface KategoriaRepository extends JpaRepository<Kategoria, Long>{

    boolean existsByNimi(String nimi);
    List<Kategoria> findByNimi(String nimi);
}
