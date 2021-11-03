package si.fri.prpo.polnilnice.entitete;

import javax.persistence.*;

@Entity(name = "najem")
@NamedQueries(value =
        {
                @NamedQuery(name = "Najem.getAll", query = "SELECT n FROM najem n"),
                @NamedQuery(name = "Najem.getById", query = "SELECT n FROM najem n WHERE n.najem_id = :najem_id"),
                @NamedQuery(name = "Najem.getByTermin", query = "SELECT n FROM najem n WHERE n.termin = :termin"),
                @NamedQuery(name = "Najem.getAllByPolnilnica", query = "SELECT n FROM najem n WHERE n.polnilnica_najema = :polnilnica_id"),
                @NamedQuery(name = "Najem.getAllByUporabnik", query = "SELECT n FROM najem n WHERE n.uporabnik_najema = :uporabnik_id"),
        })
public class Najem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer najem_id;
    private String termin;

    @ManyToOne
    @JoinColumn(name = "uporabnik")
    private Uporabnik uporabnik_najema;

    @ManyToOne
    @JoinColumn(name = "polnilnica")
    private Polnilnica polnilnica_najema;

    public Integer getNajem_id() {
        return najem_id;
    }

    public void setNajem_id(Integer najem_id) {
        this.najem_id = najem_id;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public Uporabnik getUporabnik() {
        return uporabnik_najema;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik_najema = uporabnik;
    }

    public Polnilnica getPolnilnica() {
        return polnilnica_najema;
    }

    public void setPolnilnica(Polnilnica polnilnica) {
        this.polnilnica_najema = polnilnica;
    }
}
