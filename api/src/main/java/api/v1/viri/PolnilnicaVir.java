package api.v1.viri;


import si.fri.prpo.polnilnice.entitete.Polnilnica;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("polnilnica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PolnilnicaVir {
    @GET
    public Response vrniPolnilnice(){

        ArrayList<Polnilnica> polnilnice = null;

        return Response.status(Response.Status.OK).entity(polnilnice).build();
    }
}
