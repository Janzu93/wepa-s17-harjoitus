package wepa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class FileObject extends AbstractPersistable<Long>{

    private String mediaType;
    private String name;
    private Long size;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @OneToOne
    private Uutinen uutinen;
}
