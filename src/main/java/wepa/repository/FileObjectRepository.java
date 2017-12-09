package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.FileObject;

public interface FileObjectRepository extends JpaRepository<FileObject, Long>{
}
