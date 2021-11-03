package si.fri.prpo.polnilnice.entitete;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "uporabnik")
@NamedQueries(value =
        {
                @NamedQuery(name = "Uporabnik.getAll", query = "SELECT u FROM uporabnik u"),
                @NamedQuery(name = "Uporabnik.getById", query = "SELECT u FROM uporabnik u WHERE u.uporabnik_id = :uporabnik_id"),
                @NamedQuery(name = "Uporabnik.getByType", query = "SELECT u FROM uporabnik u WHERE u.je_lastnik = :je_lastnik"),
                @NamedQuery(name = "Uporabnik.getIme", query = "SELECT u.uporabnik_ime FROM uporabnik u WHERE u.uporabnik_id = :uporabnik_id"),
                @NamedQuery(name = "Uporabnik.getPriimek", query = "SELECT u.uporabnik_priimek FROM uporabnik u WHERE u.uporabnik_id = :uporabnik_id"),
                @NamedQuery(name = "Uporabnik.getByName", query = "SELECT u FROM uporabnik u WHERE u.uporabnik_ime = :uporabnik_ime")
        })
public class Uporabnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uporabnik_id;

    private String uporabnik_ime;

    private String uporabnik_priimek;

    private boolean je_lastnik;

    private String kontakt;

    @OneToMany
    private List<Polnilnica> uporabnik_polnilnice = new ArrayList<>();

    @OneToMany
    private List<Najem> uporabnik_najem = new ArrayList<>();

    public Integer getUporabnik_id() {
        return uporabnik_id;
    }

    public void setUporabnik_id(Integer uporabnik_id) {
        this.uporabnik_id = uporabnik_id;
    }

    public String getUporabnik_ime() {
        return uporabnik_ime;
    }

    public void setUporabnik_ime(String uporabnik_ime) {
        this.uporabnik_ime = uporabnik_ime;
    }

    public String getUporabnik_priimek() {
        return uporabnik_priimek;
    }

    public void setUporabnik_priimek(String uporabnik_priimek) {
        this.uporabnik_priimek = uporabnik_priimek;
    }

    public boolean getJeLastnik() {
        return je_lastnik;
    }

    public void setJeLastnik(boolean jeLastnik) {
        this.je_lastnik = jeLastnik;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public List<Polnilnica> getUporabnik_polnilnice() {
        return uporabnik_polnilnice;
    }

    public void setUporabnik_polnilnice(List<Polnilnica> uporabnik_polnilnice) {
        this.uporabnik_polnilnice = uporabnik_polnilnice;
    }

    public List<Najem> getUporabnik_najem() {
        return uporabnik_najem;
    }

    public void setUporabnik_najem(List<Najem> uporabnik_najem) {
        this.uporabnik_najem = uporabnik_najem;
    }
}
