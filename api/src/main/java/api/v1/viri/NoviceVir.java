package api.v1.viri;

import zrna.OcenaZrno;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;
import java.util.logging.Logger;

@Path("novice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class NoviceVir {
    @Context
    protected UriInfo uriInfo;

    private static Logger log = Logger.getLogger(NoviceVir.class.getName());

    private Client httpClient;

    private String baseUrl;

    private static String beanId;

    private Response r;

    @PostConstruct
    private void init(){
        httpClient = ClientBuilder.newClient();
        baseUrl = "http://localhost:8082/v1";
    }

    @GET
    public Response pridobiNovice(){
        try{
             r =  httpClient
                    .target(baseUrl + "/novice/")
                    .request(MediaType.APPLICATION_JSON)
                    .get();
        } catch (WebApplicationException | ProcessingException e){
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        //log.info(r.readEntity(String.class));
        String x = r.readEntity(String.class);
        //log.info("OK");
        return Response
                .status(Response.Status.OK)
                .entity(x)
                .build();
    }
}
