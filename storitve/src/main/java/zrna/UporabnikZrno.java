package zrna;

import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
//import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class UporabnikZrno {

    //@PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;


    public List<Uporabnik> getUporabniki() {
        em = Persistence.createEntityManagerFactory("polnilne-postaje-jpa").createEntityManager();
        List<Uporabnik> vsiUporabniki = em.createNamedQuery("Uporabnik.getAll",Uporabnik.class).getResultList();
        return vsiUporabniki;
    }

    public List<Uporabnik> getUporabnikiAPI(){
        em = Persistence.createEntityManagerFactory("polnilne-postaje-jpa").createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Uporabnik> c = cb.createQuery(Uporabnik.class);
        Root<Uporabnik> emp = c.from(Uporabnik.class);
        c.select(emp);

        return em.createQuery(c).getResultList();

    }
}
