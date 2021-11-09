package zrna;

import si.fri.prpo.polnilnice.entitete.Najem;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NamedAttributeNode;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;

//@RequestScoped
@ApplicationScoped
public class NajemZrno {

    private static Logger log = Logger.getLogger(NajemZrno.class.getName());
    private static String beanId;

    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

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
    public Najem pridobiNajem(int id){
        Najem n = null;
        try {
            n = em.find(Najem.class,id);
        }
        catch (Exception e){
            log.severe("Uporabnik ne obstaja");
        }
        return n;
    }

    @Transactional
    public Najem ustvariNajem(Najem n){
        em = Persistence.createEntityManagerFactory("polnilne-postaje-jpa").createEntityManager();
        Najem novNajem = new Najem();
        if(n != null){
            novNajem.setTermin(n.getTermin());
            em.persist(novNajem);
        }
        return novNajem;
    }


    @Transactional
    public boolean odstraniNajem(int id){
        Polnilnica delPolnilnica = em.find(Polnilnica.class,id);

        if(delPolnilnica != null) {
            try {
                em.remove(delPolnilnica);
                return true;
            } catch (Exception e) {
                log.severe("Napaka pri odstranjevanju");
            }
        }
        return false;
    }

    public Najem posodobiNajem(Najem n, int id){
        Najem novNajem = em.find(Najem.class, id);
        if(n != null && novNajem != null){
            novNajem.setTermin(n.getTermin());
            novNajem = em.merge(novNajem);
            return novNajem;
        }
        return null;
    }
}
