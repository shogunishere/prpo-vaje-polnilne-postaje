package api.v1.viri;


import si.fri.prpo.polnilnice.entitete.Najem;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("najemi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class NajemVir {
    @GET
    public Response vrniNajeme(){

        ArrayList<Najem> najemi = null;

        return Response.status(Response.Status.OK).entity(najemi).build();
    }
}
