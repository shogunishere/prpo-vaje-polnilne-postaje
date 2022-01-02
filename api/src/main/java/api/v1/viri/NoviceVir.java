package api.v1.viri;

import dtos.NoviceDTO;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.util.List;
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
        baseUrl = "http://host.docker.internal:8082/v1";
    }

    @GET
    public Response pridobiNovice(@RequestBody List<NoviceDTO> novice){
        log.info("GET");
        NoviceDTO[] noviceDTOS;
        String stringifiedResponse;
        try{
             r =  httpClient
                    .target(baseUrl + "/novice/")
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            stringifiedResponse = r.readEntity(String.class);
            JSONArray jsonArray = new JSONArray(stringifiedResponse);
            noviceDTOS = new NoviceDTO[jsonArray.length()];

            // build NoviceDTOs
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject novica = (JSONObject) jsonArray.get(i);

                NoviceDTO novicaDTO = new NoviceDTO();

                novicaDTO.setNaslov(novica.getString("title"));
                novicaDTO.setSource(novica.getString("source"));
                novicaDTO.setUrl(novica.getString("url"));

                noviceDTOS[i] = novicaDTO;
            }

            log.info(jsonArray.get(0).toString());

        } catch (WebApplicationException | ProcessingException e){
            log.info("error");
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

        log.info("OK");
        return Response
                .status(Response.Status.OK)
                .entity(stringifiedResponse)
                .build();
    }
}
