package api.v1.viri;


import anotacije.BeleziKlice;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import zrna.UporabnikZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UporabnikVir {

    @Inject
    private UporabnikZrno up;


    @GET
    @BeleziKlice
    public Response vrniUporabnike(){
        List<Uporabnik> uporabniki = up.getUporabniki();
        return Response
                .status(Response.Status.OK)
                .entity(uporabniki).build();
    }

    @Path("{id}")
    @GET
    @BeleziKlice
    public Response vrniUporabnika(@PathParam("id") int id){
        Uporabnik uporabnik = up.pridobiUporabnika(id);

        if(uporabnik == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(uporabnik).build();
    }

    @POST//MALONE
    @BeleziKlice
    public Response ustvariUporabnika(Uporabnik uporabnik){
        return Response.status(Response.Status.OK).entity(up.ustvariUporabnika(uporabnik)).build();
    }

    @PUT
    @BeleziKlice
    @Path("{id}")
    public Response posodobiUporabnika(@PathParam("id") int id, Uporabnik uporabnik){
        Uporabnik temp = up.posodobiUporabnika(uporabnik,id);
        if(temp == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(temp).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniUporabnika(@PathParam("id") int id){
        boolean temp = up.odstraniUporabnika(id);
        if(temp == false){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();

    }

}
















