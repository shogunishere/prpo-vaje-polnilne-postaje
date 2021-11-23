package api.v1.viri;


import anotacije.BeleziKlice;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import zrna.PolnilnicaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("polnilnice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PolnilnicaVir {
    @Inject
    PolnilnicaZrno p;

    @GET
    @BeleziKlice
    public Response vrniPolnilnice(){
        List<Polnilnica> polnilnica = p.pridobiVsePolnilnice();
        return Response
                .status(Response.Status.OK)
                .entity(polnilnica).build();
    }

    @Path("{id}")
    @GET
    @BeleziKlice
    public Response vrniPolnilnico(@PathParam("id") int id){
        Polnilnica polnilnica = p.pridobiPolnilnico(id);

        if(polnilnica == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(polnilnica).build();
    }

    @POST//MALONE
    @BeleziKlice
    public Response ustvariPolnilnico(Polnilnica polnilnica){
        return Response.status(Response.Status.OK).entity(p.ustvariPolnilnico(polnilnica)).build();
    }

    @PUT
    @BeleziKlice
    @Path("{id}")
    public Response posodobiPolnilnico(@PathParam("id") int id, Polnilnica polnilnica){
        Polnilnica temp = p.posodobiPolnilnico(polnilnica,id);
        if(temp == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(temp).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniPolnilnico(@PathParam("id") int id){
        boolean temp = p.odstraniPolnilnico(id);
        if(temp == false){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();

    }

}
