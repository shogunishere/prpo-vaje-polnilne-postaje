package zrna;

import anotacije.BeleziKlice;
import dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.entitete.Najem;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class PolnilnicaZrno {
    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;
    private static String beanId;

    private static Logger log = Logger.getLogger(PolnilnicaZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("Construct");
        beanId = UUID.randomUUID().toString();
        log.info("ID Polnilnica Fižol: "+ beanId);
    }

    @PreDestroy
    private void destructor(){

        log.info("Destroy");
    }

    @BeleziKlice
    @Transactional
    public Polnilnica pridobiPolnilnico(int id){
        Polnilnica p = null;
        try {
            p = em.find(Polnilnica.class,id);
        }
        catch (Exception e){
            log.severe("Polnilnica ne obstaja");
        }
        return p;
    }
    @BeleziKlice
    @Transactional
    public List<Polnilnica> pridobiVsePolnilnice(){
        List<Polnilnica> vsePolnilnice = em.createNamedQuery("Polnilnica.getAll", Polnilnica.class).getResultList();
        return vsePolnilnice;
    }
    @BeleziKlice
    @Transactional
    public Polnilnica ustvariPolnilnico(Polnilnica p){
        Polnilnica novaPolnilnica = new Polnilnica();
        if(p != null){
            novaPolnilnica.setPolnilnica_ime(p.getPolnilnica_ime());
            novaPolnilnica.setCena(p.getCena());
            novaPolnilnica.setStPrikljuckov(p.getStPrikljuckov());
            novaPolnilnica.setDelovni_cas(p.getDelovni_cas());
            em.persist(novaPolnilnica);
        }
        return novaPolnilnica;
    }

    @BeleziKlice
    @Transactional
    public boolean odstraniPolnilnico(int id){
        Polnilnica delPolnilnica = em.find(Polnilnica.class,id);

        if(delPolnilnica != null) {
            try {
                //em.getTransaction().begin();
                em.remove(delPolnilnica);
                //em.getTransaction().commit();

                return true;
            } catch (Exception e) {
                log.severe("Napaka pri odstranjevanju");
            }
        }
        return false;
    }
    @BeleziKlice
    @Transactional
    public Polnilnica posodobiPolnilnico(Polnilnica p, int id){
        Polnilnica novaPolnilnica = em.find(Polnilnica.class, id);
        if(p != null && novaPolnilnica != null){
            novaPolnilnica.setPolnilnica_ime(p.getPolnilnica_ime());
            novaPolnilnica.setCena(p.getCena());
            novaPolnilnica.setStPrikljuckov(p.getStPrikljuckov());
            novaPolnilnica.setDelovni_cas(p.getDelovni_cas());
            novaPolnilnica = em.merge(novaPolnilnica);
            return novaPolnilnica;
        }
        return null;
    }
}
