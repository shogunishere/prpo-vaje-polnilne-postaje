package servlets;

import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import si.fri.prpo.polnilnice.entitete.Najem;
import zrna.UporabnikZrno;
import zrna.NajemZrno;
import zrna.PolnilnicaZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject
    private PolnilnicaZrno polnilnicaZrno;

    @Inject
    private NajemZrno najemZrno;

    //For debugging. Has to be in every class separatley
    private static Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start of get method");

        //For writing html as response
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter prt = resp.getWriter();

        List<Uporabnik> vsiUporabniki = uporabnikZrno.getUporabniki();


        for(Uporabnik i : vsiUporabniki){
            prt.println(i.getUporabnik_ime());
        }

        prt.println("<br>");

        List<Uporabnik> vsiUporabnikiAPI = uporabnikZrno.getUporabnikiAPI();

        for(Uporabnik j : vsiUporabnikiAPI){
            prt.println(j.getUporabnik_ime());
            prt.println(j.getUporabnik_priimek());
            prt.println(j.getJeLastnik());
            prt.println("<br>");
        }

        //nov objekt
        //Uporabnik
        Uporabnik novUporabnik = new Uporabnik();
        novUporabnik.setUporabnik_ime("John");
        novUporabnik.setUporabnik_priimek("Doe");
        novUporabnik.setJeLastnik(true);
        novUporabnik.setKontakt("jd@gmail.com");

        Uporabnik u = uporabnikZrno.ustvariUporabnika(novUporabnik);

        u.setUporabnik_ime("Joe");
        u.setJeLastnik(false);
        u = uporabnikZrno.posodobiUporabnika(u,u.getUporabnik_id());
        uporabnikZrno.odstraniUporabnika(u.getUporabnik_id());

        //Polnilnice
        Polnilnica novaPolnilnica = new Polnilnica();
        novaPolnilnica.setPolnilnica_ime("Polnilnica Zelena Jama");
        novaPolnilnica.setCena(2);
        novaPolnilnica.setStPrikljuckov(4);
        novaPolnilnica.setDelovni_cas("7:00-17:00");

        Polnilnica p = polnilnicaZrno.ustvariPolnilnico(novaPolnilnica);

        p.setPolnilnica_ime("Polnilnica bežigrad");
        p.setCena(3);
        log.info(p.getPolnilnica_ime());
        p = polnilnicaZrno.posodobiPolnilnico(p, p.getPolnilnica_id());
        log.info(p.getPolnilnica_ime());
        polnilnicaZrno.odstraniPolnilnico(p.getPolnilnica_id());

        //Najem
        Najem novNajem = new Najem();
        novNajem.setTermin("Jutri");

        Najem n = najemZrno.ustvariNajem(novNajem);
        log.info(n.getTermin());
        n.setTermin("Vceraj");
        log.info(n.getTermin());
        n = najemZrno.posodobiNajem(n,n.getNajem_id());
        najemZrno.odstraniNajem(n.getNajem_id());


        //Bean destroy???

    }
}
