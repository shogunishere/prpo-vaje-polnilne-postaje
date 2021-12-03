package api.v1.viri;


import anotacije.BeleziKlice;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import zrna.NajemZrno;
import zrna.UporabnikZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UporabnikVir {

    @Inject
    private UporabnikZrno up;


    @Context
    protected UriInfo uriInfo;

    private static Logger log = Logger.getLogger(UporabnikVir.class.getName());


    @GET
    public Response vrniUporabnike(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        //List<Uporabnik> vsiUporabniki = JPAUtils.queryEntities(em, Uporabnik.class, query);
        //Long stVsehUporabnikov = JPAUtils.queryEntitiesCount(em, Uporabnik.class, query);
        List<Uporabnik> vsiUporabniki = up.getUporabniki(query);
        Long stVsehUporabnikov = up.getUporabnikCount(query);
        //log.info(String.valueOf(stVsehUporabnikov.intValue()));

        //deluje samo za filter????
        return Response
                .status(Response.Status.OK)
                .entity(vsiUporabniki)
                .header("X-Total-Count",stVsehUporabnikov)
                .build();
    }

    @Path("{id}")
    @GET
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
    public Response ustvariUporabnika(Uporabnik uporabnik){
        return Response.status(Response.Status.OK).entity(up.ustvariUporabnika(uporabnik)).build();
    }

    @PUT
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
















