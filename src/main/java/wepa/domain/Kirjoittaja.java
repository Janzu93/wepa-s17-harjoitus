package wepa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kirjoittaja extends AbstractPersistable<Long> {

    private String nimi;

    @Cascade(CascadeType.ALL)
    @ManyToMany
    private List<Uutinen> uutiset = new ArrayList<>();

    public Kirjoittaja(String nimi) {
        this.nimi = nimi;
    }
}
