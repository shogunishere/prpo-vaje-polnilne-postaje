package zrna;

import anotacije.BeleziKlice;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.polnilnice.entitete.Najem;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
//import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UporabnikZrno {
    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

    private static Logger log = Logger.getLogger(UporabnikZrno.class.getName());
    private static String beanId;

    @Inject
    PolnilnicaZrno polnilnicaZrno;

    @Inject
    NajemZrno najemZrno;


    @PostConstruct
    private void init(){
        log.info("Construct");
        beanId = UUID.randomUUID().toString();
        log.info("ID Uporabnik Zrna: "+ beanId);
    }

    @PreDestroy
    private void destructor(){
        log.info("Destroy");
    }


    public List<Uporabnik> getUporabniki() {
        List<Uporabnik> vsiUporabniki = em.createNamedQuery("Uporabnik.getAll",Uporabnik.class).getResultList();
        return vsiUporabniki;
    }

    public List<Uporabnik> getUporabnikiAPI(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Uporabnik> c = cb.createQuery(Uporabnik.class);
        Root<Uporabnik> emp = c.from(Uporabnik.class);
        c.select(emp);

        return em.createQuery(c).getResultList();
    }

    @BeleziKlice
    @Transactional
    public Uporabnik pridobiUporabnika(int id){
        Uporabnik u = null;
        try {
            u = em.find(Uporabnik.class,id);
        }
        catch (Exception e){
            log.severe("Uporabnik ne obstaja");
        }
        return u;
    }
    @BeleziKlice
    @Transactional
    public Uporabnik ustvariUporabnika(Uporabnik u){
        if(u != null){
            //em.getTransaction().begin();
            em.persist(u);
            //em.getTransaction().commit();
        }
        return u;
    }

    @BeleziKlice
    @Transactional
    public boolean odstraniUporabnika(int id){
        Uporabnik delUporabnik = em.find(Uporabnik.class,id);
        List<Najem> vsiNajemi = najemZrno.pridobiVseNajeme();
        List<Polnilnica> vsePolnilnice = polnilnicaZrno.pridobiVsePolnilnice();

        for(Najem n : vsiNajemi){
            if(n.getUporabnik().getUporabnik_id() == id){
                najemZrno.odstraniNajem(n.getNajem_id());
            }
        }
        for(Polnilnica p : vsePolnilnice){
            if(p.getUporabnik().getUporabnik_id() == id){
                polnilnicaZrno.odstraniPolnilnico(p.getPolnilnica_id());
            }
        }

        if(delUporabnik != null) {
            try {
                //em.getTransaction().begin();
                em.remove(delUporabnik);
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
    public Uporabnik posodobiUporabnika(Uporabnik u, int id){
        Uporabnik update = em.find(Uporabnik.class, id);
        if(u != null && update != null){
            update.setUporabnik_ime(u.getUporabnik_ime());
            update.setUporabnik_priimek(u.getUporabnik_priimek());
            update.setJeLastnik(u.getJeLastnik());
            update.setKontakt(u.getKontakt());
            //em.getTransaction().begin();
            update = em.merge(update);
            //em.getTransaction().commit();
            return update;
        }
        return null;
    }
    @BeleziKlice
    public Long getUporabnikCount(QueryParameters query){
        return JPAUtils.queryEntitiesCount(em, Uporabnik.class, query);
    }
}
