package api.v1.viri;


import anotacije.BeleziKlice;
import si.fri.prpo.polnilnice.entitete.Najem;
import zrna.NajemZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("najemi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class NajemVir {

    @Inject
    private NajemZrno n;

    @GET
    @BeleziKlice
    public Response vrniNajeme(){
        List<Najem> najemi = n.pridobiVseNajeme();
        return Response
                .status(Response.Status.OK)
                .entity(najemi).build();
    }
    @BeleziKlice
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

    @POST//MALONE
    @BeleziKlice
    public Response ustvariNajem(Najem najem){
        return Response.status(Response.Status.OK).entity(n.ustvariNajem(najem)).build();
    }

    @PUT
    @BeleziKlice
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
        return Response.status(Response.Status.OK).build();
    }

}
