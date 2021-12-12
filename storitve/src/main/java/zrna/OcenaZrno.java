package zrna;


import dtos.OcenaDTO;
import si.fri.prpo.polnilnice.entitete.Ocena;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class OcenaZrno {

    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;
    private static String beanId;

    @Inject
    private PolnilnicaZrno polnilnica;

    private static Logger log = Logger.getLogger(PolnilnicaZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Construct ocena");
        beanId = UUID.randomUUID().toString();
        log.info("ID Ocena Fižol: "+ beanId);
    }

    @PreDestroy
    private void destructor(){
        log.info("Destroy");
    }

    @Transactional
    public Ocena ustvariOceno(OcenaDTO ocena){
        Ocena novaOcena = new Ocena();
        if(ocena != null){
            novaOcena.setOcena_besedilo(ocena.getOcena_besedilo());
            novaOcena.setOcena_stevilo_zvezdic(ocena.getOcena_stevilo_zvezdic());

            Query temp = em.createNamedQuery("Polnilnica.getById", Polnilnica.class);
            temp.setParameter("polnilnica_id",ocena.getPolnilnica_id());

            List<Polnilnica> pTemp = temp.getResultList();
            Polnilnica p = pTemp.get(0);
            novaOcena.setPolnilnica(p);
            em.persist(novaOcena);
        }
        return novaOcena;
    }


}
