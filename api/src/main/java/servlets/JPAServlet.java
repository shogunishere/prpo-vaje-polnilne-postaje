package servlets;

import si.fri.prpo.polnilnice.entitete.Uporabnik;
import zrna.UporabnikZrno;

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
    //For debugging. Has to be in every class separatley
    private static Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start of get method");

        //For writing html as response
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter prt = resp.getWriter();

        uporabnikZrno = new UporabnikZrno();
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
    }
}
