package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wepa.domain.Kategoria;

@Repository
public interface KategoriaRepository extends JpaRepository<Kategoria, Long>{
}
