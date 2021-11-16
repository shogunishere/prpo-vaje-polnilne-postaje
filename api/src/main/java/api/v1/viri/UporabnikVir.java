package api.v1.viri;


import anotacije.BeleziKlice;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UporabnikVir {

    @BeleziKlice
    @GET
    public Response vrniUporabnike(){

        ArrayList<Uporabnik> uporabniki = null;

        return Response.status(Response.Status.OK).entity(uporabniki).build();
    }
}
