package servlets;

import dtos.NajemDTO;
import dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import si.fri.prpo.polnilnice.entitete.Najem;
import zrna.UporabnikZrno;
import zrna.NajemZrno;
import zrna.PolnilnicaZrno;
import zrna.UpravljanjeNajemovZrno;

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

    @Inject
    private UpravljanjeNajemovZrno upravljanjeNajemovZrno;

    //For debugging. Has to be in every class separatley
    private static Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start of get method");

        //For writing html as response
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter prt = resp.getWriter();


        //DTO tesiranje najema
        Najem n = najemZrno.pridobiNajem(1);

        NajemDTO najemDTO = new NajemDTO();
        najemDTO.setPolnilnica_najema(n.getPolnilnica());
        najemDTO.setUporabnik_najema(n.getUporabnik());
        najemDTO.setTermin(n.getTermin());

        log.info(""+n);
        PolnilnicaDTO pDTO = new PolnilnicaDTO();
        Polnilnica pol = polnilnicaZrno.pridobiPolnilnico(najemDTO.getPolnilnica_najema().getPolnilnica_id());
        pDTO.setCena(pol.getCena());
        pDTO.setSt_prikljuckov(pol.getStPrikljuckov());
        pDTO.setDelovni_cas(pol.getDelovni_cas());
        pDTO.setPolnilnica_ime(pol.getPolnilnica_ime());
        pDTO.setUporabnik(pol.getUporabnik());

        upravljanjeNajemovZrno.rezervacijaNajema(najemDTO);
        log.info(""+upravljanjeNajemovZrno.izracunCenePolnjenja(najemDTO.getTermin(),pDTO));

        upravljanjeNajemovZrno.prekliciNajem(najemDTO);


        //Bean destroy???

    }
}
