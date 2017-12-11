package wepa.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.AbstractPersistable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Uutinen extends AbstractPersistable<Long> {

    private String otsikko;

    @Column(length = 5000)
    private String ingressi;

    @Column(length = 10000)
    private String sisalto;
    private LocalDate julkaisupaiva;

    @Cascade(CascadeType.ALL)
    @ManyToMany(mappedBy = "uutiset")
    private List<Kirjoittaja> kirjoittajat = new ArrayList<>();

    @Cascade(CascadeType.ALL)
    @ManyToMany(mappedBy = "uutiset")
    private List<Kategoria> kategoriat = new ArrayList<>();

    public Uutinen(String otsikko, String ingressi, String sisalto, LocalDate julkaisupaiva) {
        this.otsikko = otsikko;
        this.ingressi = ingressi;
        this.sisalto = sisalto;
        this.julkaisupaiva = julkaisupaiva;
    }
}
