package dtos;


import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

public class NajemDTO {
    private String termin;
    private Uporabnik uporabnik_najema;
    private Polnilnica polnilnica_najema;

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public Uporabnik getUporabnik_najema() {
        return uporabnik_najema;
    }

    public void setUporabnik_najema(Uporabnik uporabnik_najema) {
        this.uporabnik_najema = uporabnik_najema;
    }

    public Polnilnica getPolnilnica_najema() {
        return polnilnica_najema;
    }

    public void setPolnilnica_najema(Polnilnica polnilnica_najema) {
        this.polnilnica_najema = polnilnica_najema;
    }
}
