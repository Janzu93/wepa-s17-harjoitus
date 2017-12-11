package wepa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Kategoria extends AbstractPersistable<Long>{

    private String nimi;

    @ManyToMany
    private List<Uutinen> uutiset = new ArrayList<>();

    public Kategoria(String nimi) {
        this.nimi = nimi;
    }
}
