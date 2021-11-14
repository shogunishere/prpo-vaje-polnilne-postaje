package zrna;


import dtos.NajemDTO;
import dtos.PolnilnicaDTO;
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
    @Inject
    private NajemZrno najemZrno;
    @Inject
    private PolnilnicaZrno polnilnicaZrno;

    private static Logger log = Logger.getLogger(UporabnikZrno.class.getName());
    private static String beanId;

    @PostConstruct
    private void init(){
        log.info("Construct");
        beanId = UUID.randomUUID().toString();
        log.info("ID Poslovne logike: "+ beanId);
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

    @Transactional
    public void prekliciNajem(NajemDTO najem){
        int polnilnica_id = najem.getPolnilnica_najema().getPolnilnica_id();
        int uporabnik_id = najem.getUporabnik_najema().getUporabnik_id();
        String termin = najem.getTermin();

        List<Najem> vsi = najemZrno.pridobiNajemByAll(polnilnica_id,uporabnik_id,termin);


        if(vsi.size() == 0){
            log.info("Najem ne obstaja");
            return;
        }
        Najem n = vsi.get(0);
        najemZrno.odstraniNajem(n.getNajem_id());
    }

    public float izracunCenePolnjenja(String termin, PolnilnicaDTO p){
        String date = termin.split(",")[0];
        String time = termin.split(",")[1];
        String startTime = time.split("-")[0];
        String endTime = time.split("-")[1];

        String startHours = startTime.split(":")[0];
        String startMinutes = startTime.split(":")[1];
        String endHours = endTime.split(":")[0];
        String endMinutes = endTime.split(":")[1];

        int startHoursInt = Integer.parseInt(startHours);
        int startMinutesInt = Integer.parseInt(startMinutes);

        int endHoursInt = Integer.parseInt(endHours);
        int endMinutesInt = Integer.parseInt(endMinutes);

        int cenaPolnjenja = p.getCena();


        float cena = (((60*endHoursInt+endMinutesInt) - (60*startHoursInt+startMinutesInt))/60)*cenaPolnjenja;

        return cena;


    }

}
