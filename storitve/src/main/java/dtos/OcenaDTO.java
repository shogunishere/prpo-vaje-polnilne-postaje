package dtos;

public class OcenaDTO {
    private String ocena_besedilo;
    private Integer ocena_stevilo_zvezdic;
    private Integer polnilnica_id;

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

    public Integer getPolnilnica_id() {
        return polnilnica_id;
    }

    public void setPolnilnica_id(Integer polnilnica_id) {
        this.polnilnica_id = polnilnica_id;
    }
}
