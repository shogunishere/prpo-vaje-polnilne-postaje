package api.v1.viri;


import dtos.NajemDTO;
import dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.entitete.Najem;
import zrna.NajemZrno;
import zrna.UpravljanjeNajemovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("najemi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class NajemVir {

    @Inject
    private NajemZrno n;

    @Inject
    private UpravljanjeNajemovZrno up;

    private static Logger log = Logger.getLogger(NajemVir.class.getName());


    @GET
    public Response vrniNajeme(){
        List<Najem> najemi = n.pridobiVseNajeme();
        return Response
                .status(Response.Status.OK)
                .entity(najemi).build();
    }
    @Path("{id}")
    @GET
    public Response vrniNajem(@PathParam("id") int id){
        Najem najem = n.pridobiNajem(id);

        if(najem == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(najem).build();
    }

    @POST
    public Response ustvariNajem(Najem najem){
        return Response.status(Response.Status.OK).entity(n.ustvariNajem(najem)).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiNajem(@PathParam("id") int id, Najem najem){
        Najem temp = n.posodobiNajem(najem,id);
        if(temp == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(temp).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniNajem(@PathParam("id") int id){
        boolean temp = n.odstraniNajem(id);
        if(temp == false){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(temp).build();
    }

    @POST
    @Path("rezerviraj_najem")
    public Response narediRezervacijo(Najem najem){
        NajemDTO n = new NajemDTO();
        n.setTermin(najem.getTermin());
        n.setUporabnik_najema(najem.getUporabnik());
        n.setPolnilnica_najema(najem.getPolnilnica());
        return Response.status(Response.Status.OK).entity(up.rezervacijaNajema(n)).build();
    }

    @DELETE
    @Path("preklici_najem")
    public Response prekliciNajem(Najem najem){
        NajemDTO n = new NajemDTO();
        n.setTermin(najem.getTermin());
        n.setUporabnik_najema(najem.getUporabnik());
        n.setPolnilnica_najema(najem.getPolnilnica());
        up.prekliciNajem(n);
        return Response.status(Response.Status.OK).entity("Preklic Uspešen").build();
    }

    @GET
    @Path("izracunaj")
    public Response izracunCeneNajema(Najem najem){
        NajemDTO n = new NajemDTO();
        n.setTermin(najem.getTermin());
        n.setUporabnik_najema(najem.getUporabnik());
        n.setPolnilnica_najema(najem.getPolnilnica());

        PolnilnicaDTO p = new PolnilnicaDTO();
        p.setCena(najem.getPolnilnica().getCena());

        return Response.status(Response.Status.OK).entity(up.izracunCenePolnjenja(najem.getTermin(),p)).build();
    }

}
