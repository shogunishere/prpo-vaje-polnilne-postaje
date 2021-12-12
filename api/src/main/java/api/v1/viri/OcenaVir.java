package api.v1.viri;


import dtos.OcenaDTO;
import si.fri.prpo.polnilnice.entitete.Ocena;
import zrna.NajemZrno;
import zrna.OcenaZrno;
import zrna.PolnilnicaZrno;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;
import java.util.logging.Logger;

@Path("ocene")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class OcenaVir {


    @Context
    protected UriInfo uriInfo;

    @Inject
    OcenaZrno oz;

    private static Logger log = Logger.getLogger(OcenaVir.class.getName());

    @POST
    public Response ustvariOceno(OcenaDTO o){
        return Response.status(201).entity(oz.ustvariOceno(o)).build();
    }
}
