package si.fri.prpo.polnilnice.entitete;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "polnilnica")
@NamedQueries(value =
        {
                @NamedQuery(name = "Polnilnica.getAll", query = "SELECT p FROM polnilnica p"),
                @NamedQuery(name = "Polnilnica.getById", query = "SELECT p FROM polnilnica p WHERE p.polnilnica_id = :polnilnica_id"),
                @NamedQuery(name = "Polnilnica.getCena", query = "SELECT p.cena FROM polnilnica p WHERE p.polnilnica_id = :polnilnica_id"),
                @NamedQuery(name = "Polnilnica.getIme", query = "SELECT p.polnilnica_ime FROM polnilnica p WHERE p.polnilnica_id = :polnilnica_id"),
                @NamedQuery(name = "Uporabnik.getByName", query = "SELECT p FROM polnilnica p WHERE p.polnilnica_ime = :polnilnica_ime"),
                @NamedQuery(name = "Uporabnik.getBySpec", query = "SELECT p FROM polnilnica p WHERE p.stPrikljuckov = :stPrikljuckov"),
                @NamedQuery(name = "Uporabnik.getByMaxCena", query = "SELECT p FROM polnilnica p WHERE p.cena <= :cena")
        })
public class Polnilnica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer polnilnica_id;
    private String polnilnica_ime;
    private int cena;
    private String delovni_cas;
    private int stPrikljuckov;

    @OneToMany(mappedBy = "polnilnica")
    private List<Najem> polnilnica_najemi = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "uporabnik")
    private Uporabnik uporabnik;

    public Integer getPolnilnica_id() {
        return polnilnica_id;
    }

    public void setPolnilnica_id(Integer polnilnica_id) {
        this.polnilnica_id = polnilnica_id;
    }

    public String getPolnilnica_ime() {
        return polnilnica_ime;
    }

    public void setPolnilnica_ime(String polnilnica_ime) {
        this.polnilnica_ime = polnilnica_ime;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public String getDelovni_cas() {
        return delovni_cas;
    }

    public void setDelovni_cas(String delovni_cas) {
        this.delovni_cas = delovni_cas;
    }

    public int getStPrikljuckov() {
        return stPrikljuckov;
    }

    public void setStPrikljuckov(int stPrikljuckov) {
        this.stPrikljuckov = stPrikljuckov;
    }

    public List<Najem> getPolnilnica_najemi() {
        return polnilnica_najemi;
    }

    public void setPolnilnica_najemi(List<Najem> polnilnica_najemi) {
        this.polnilnica_najemi = polnilnica_najemi;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }
}