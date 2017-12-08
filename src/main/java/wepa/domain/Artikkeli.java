package wepa.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Artikkeli extends AbstractPersistable<Long> {

    private String otsikko;
    private String ingressi;
//    private String kuva;
    private String sisalto;
    private String julkaisupaiva;
}
