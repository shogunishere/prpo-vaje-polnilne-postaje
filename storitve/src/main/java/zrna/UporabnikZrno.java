package zrna;

import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;

@ApplicationScoped
public class UporabnikZrno {

    @PersistenceContext(unitName = "polnilne-postaje-jpa")
    private EntityManager em;

    public List<Uporabnik> getUporabniki() {
        //ne deluje brez createEntityManagerFactory. Zakaj????
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("polnilne-postaje-jpa"); //returns null
        //em = emf.createEntityManager(); //null pointer exception on this line. emf is null
        TypedQuery<Uporabnik> query = em.createNamedQuery("Uporabnik.getAll",Uporabnik.class);
        List<Uporabnik> vsiUporabniki = query.getResultList();

        return vsiUporabniki;
    }
}
