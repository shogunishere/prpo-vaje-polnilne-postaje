package dtos;

import si.fri.prpo.polnilnice.entitete.Uporabnik;

public class PolnilnicaDTO {
    private String polnilnica_ime;
    private int cena;
    private String delovni_cas;
    private int st_prikljuckov;
    private Uporabnik uporabnik;


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

    public int getSt_prikljuckov() {
        return st_prikljuckov;
    }

    public void setSt_prikljuckov(int st_prikljuckov) {
        this.st_prikljuckov = st_prikljuckov;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }
}
