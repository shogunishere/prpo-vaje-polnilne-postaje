package si.fri.prpo.polnilnice.entitete;

import javax.persistence.*;

@Entity(name = "ocena")
@NamedQueries(value =
        {
                @NamedQuery(name = "Ocena.getAll", query = "SELECT o FROM ocena o"),
                @NamedQuery(name = "Ocena.getById", query = "SELECT o FROM ocena o WHERE o.ocena_id = :ocena_id"),
                @NamedQuery(name = "Ocena.getByPolnilnicaId", query = "SELECT o FROM ocena o WHERE o.polnilnica.polnilnica_id = :polnilnica_id"),
        })
public class Ocena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ocena_id;

    private String ocena_besedilo;
    private Integer ocena_stevilo_zvezdic;

    @ManyToOne
    @JoinColumn(name = "polnilnica_id")
    private Polnilnica polnilnica;

    public Integer getOcena_id() {
        return ocena_id;
    }

    public void setOcena_id(Integer ocena_id) {
        this.ocena_id = ocena_id;
    }

    public String getOcena_besedilo() {
        return ocena_besedilo;
    }

    public void setOcena_besedilo(String ocena_besedilo) {
        this.ocena_besedilo = ocena_besedilo;
    }

    public Integer getOcena_stevilo_zvezdic() {
        return ocena_stevilo_zvezdic;
    }

    public void setOcena_stevilo_zvezdic(Integer ocena_stevilo_zvezdic) {
        this.ocena_stevilo_zvezdic = ocena_stevilo_zvezdic;
    }

    public Polnilnica getPolnilnica() {
        return polnilnica;
    }

    public void setPolnilnica(Polnilnica polnilnica) {
        this.polnilnica = polnilnica;
    }
}
