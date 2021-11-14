package dtos;

public class UporabnikDTO {
    private Integer uporabnik_id;
    private String uporabnik_ime;
    private String uporabnik_priimek;
    private boolean je_lastnik;
    private String kontakt;

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

    public boolean isJe_lastnik() {
        return je_lastnik;
    }

    public void setJe_lastnik(boolean je_lastnik) {
        this.je_lastnik = je_lastnik;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }
}
