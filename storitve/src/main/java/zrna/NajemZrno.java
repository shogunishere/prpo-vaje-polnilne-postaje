package zrna;

import anotacije.BeleziKlice;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import izjeme.NeveljavenNajemDtoIzjema;
import si.fri.prpo.polnilnice.entitete.Najem;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


//@ApplicationScoped
@RequestScoped
public class NajemZrno {

    private static Logger log = Logger.getLogger(NajemZrno.class.getName());
    private static String beanId;

    @Inject
    private PolnilnicaZrno pz;

    @Inject
    private UporabnikZrno uz;

    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

    @PostConstruct
    private void init(){
        log.info("Construct");
        beanId = UUID.randomUUID().toString();
        log.info("ID Najem Zrna: "+ beanId);
    }

    @PreDestroy
    private void destructor(){
        log.info("Destroy");
    }

    @BeleziKlice
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

    @BeleziKlice
    @Transactional
    public List<Najem> pridobiNajemByAll(int polnilnicaID, int uporabnikID, String termin){
        Query getNajemByAll = em.createNamedQuery("Najem.getByAll",Najem.class);
        getNajemByAll.setParameter("polnilnica_id",polnilnicaID);
        getNajemByAll.setParameter("uporabnik_id",uporabnikID);
        getNajemByAll.setParameter("termin",termin);
        List<Najem> n = getNajemByAll.getResultList();
        return n;
    }

    @BeleziKlice
    @Transactional
    public List<Najem> pridobiVseNajeme(QueryParameters q){
        List<Najem> queriedVsiNajemi = JPAUtils.queryEntities(em, Najem.class, q);
        if(queriedVsiNajemi.size() == 0){
            log.info("List najemov je prazen");
        }
        return queriedVsiNajemi;
    }

    @BeleziKlice
    @Transactional
    public List<Najem> pridobiVseNajeme(){
        List<Najem> vsiNajemi = em.createNamedQuery("Najem.getAll",Najem.class).getResultList();
        return vsiNajemi;
    }

    @BeleziKlice
    @Transactional
    public List<Najem> pridobiNajemPolnilnice(int polnilnicaID){
        Query getAllByPolnilnica = em.createNamedQuery("Najem.getAllByPolnilnica",Najem.class);
        List<Najem> vsiNajemi = getAllByPolnilnica.setParameter("polnilnica_id",polnilnicaID).getResultList();
        return vsiNajemi;
    }

    @BeleziKlice
    @Transactional
    public Najem ustvariNajem(Najem n){
        Najem novNajem = new Najem();
        if(n != null){
            novNajem.setTermin(n.getTermin());
            novNajem.setPolnilnica(n.getPolnilnica());
            novNajem.setUporabnik(n.getUporabnik());
            em.persist(novNajem);
        }
        return novNajem;
    }


    @BeleziKlice
    @Transactional
    public boolean odstraniNajem(int id){
        Najem delNajem = em.find(Najem.class,id);
        if(delNajem != null) {
            try {
                em.remove(delNajem);
                return true;
            } catch (Exception e) {
                log.severe("Napaka pri odstranjevanju");
            }
        }
        return false;
    }
    @BeleziKlice
    @Transactional
    public Najem posodobiNajem(Najem n, int id){
        Najem novNajem = em.find(Najem.class, id);
        if(n != null && novNajem != null){
            novNajem.setTermin(n.getTermin());
            novNajem = em.merge(novNajem);
            return novNajem;
        }
        return null;
    }

    @BeleziKlice
    public Long getNajemCount(QueryParameters query){
        return JPAUtils.queryEntitiesCount(em, Najem.class, query);
    }
    @BeleziKlice
    @Transactional
    public boolean validacijaNajema(Najem n){
        List<Polnilnica> vsePolnilnice = pz.pridobiVsePolnilnice();
        List<Uporabnik> vsiUporabniki = uz.getUporabniki();

        boolean containsPolnilnica = false;
        for(Polnilnica p : vsePolnilnice){
            if(p.getPolnilnica_id() == n.getPolnilnica().getPolnilnica_id()){
                containsPolnilnica = true;
            }
        }

        boolean containsUporabnik = false;
        for(Uporabnik u : vsiUporabniki){
            if(u.getUporabnik_id() == n.getUporabnik().getUporabnik_id()){
                containsUporabnik = true;
            }
        }
        if(!containsUporabnik || !containsPolnilnica){
            String msg = "Uporabnik ali Polnilnica sta neveljavna";
            throw new NeveljavenNajemDtoIzjema(msg);
        }
        return true;
    }
}
