package zrna;


import dtos.NajemDTO;
import si.fri.prpo.polnilnice.entitete.Najem;
import si.fri.prpo.polnilnice.entitete.Polnilnica;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeNajemovZrno {

    @Inject
    private UporabnikZrno uporabnikZrno;
    private NajemZrno najemZrno;
    private PolnilnicaZrno polnilnicaZrno;

    private static Logger log = Logger.getLogger(UporabnikZrno.class.getName());
    private static String beanId;

    @PostConstruct
    private void init(){
        log.info("Construct");
        beanId = UUID.randomUUID().toString();
        log.info("ID Zrna: "+ beanId);
    }

    @PreDestroy
    private void destructor(){
        log.info("Destroy");
    }

    @Transactional
    public Najem rezervacijaNajema(NajemDTO najem){

        int polnilnica_id = najem.getPolnilnica_najema().getPolnilnica_id();

        List<Najem> vsi = najemZrno.pridobiNajemPolnilnice(polnilnica_id);

        for(Najem n : vsi){
            if(n.getTermin().equals(najem.getTermin())){
                log.info("Najem za ta termin že obstaja");
                return null;
            }
        }

        Najem novNajem = new Najem();
        novNajem.setUporabnik(najem.getUporabnik_najema());
        novNajem.setPolnilnica(najem.getPolnilnica_najema());
        novNajem.setTermin(najem.getTermin());
        return najemZrno.ustvariNajem(novNajem);
    }



    //problem za remove rabimo id
    /*@Transactional
    public void prekliciNajem(NajemDTO najem){
        int polnilnica_id = najem.getPolnilnica_najema().getPolnilnica_id();

        List<Najem> vsi = najemZrno.pridobiNajemPolnilnice(polnilnica_id);

        for(Najem n : vsi){
            if(n.getTermin().equals(najem.getTermin())){
                log.info("Našli smo pravi najem");
                najemZrno.odstraniNajem();
                return;
            }
        }
        log.info("Ta termin ne obstaja");
    }*/

    //izracun število uporabnikov
    //preverba polnilica obstaja (fields)


}
