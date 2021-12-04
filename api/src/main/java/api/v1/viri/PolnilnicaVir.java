package api.v1.viri;


import anotacije.BeleziKlice;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.polnilnice.entitete.Najem;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import zrna.PolnilnicaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@Path("polnilnice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PolnilnicaVir {
    @Inject
    PolnilnicaZrno p;

    @Context
    protected UriInfo uriInfo;

    private static Logger log = Logger.getLogger(PolnilnicaVir.class.getName());


    @Operation(description = "Pridobi vse polnilnice.", summary = "Vračanje polnilnic")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List polnilnic",
                    content = @Content(schema = @Schema(implementation = Polnilnica.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih polnilnic")}
            ),
            @APIResponse(responseCode = "404",
                    description = "Polnilnice Not Found"
            )
    })
    @GET
    @BeleziKlice
    public Response vrniPolnilnice(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Polnilnica> polnilnice = p.pridobiVsePolnilnice(query);
        Long stVsehPolnilnic = p.getPolnilniceCount(query);
        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count",stVsehPolnilnic)
                .entity(polnilnice)
                .build();
    }

    @Operation(description = "Pridobi Polnilnico.", summary = "Vračanje polnilnice")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Polnilnica uspešno najden",
                    content = @Content(schema = @Schema(implementation = Polnilnica.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Polnilnica Not Found"
            )
    })
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

    @Operation(description = "Ustvari polnilnico.", summary = "Kreiranje polnilnic")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Polnilnica uspešno dodana",
                    content = @Content(schema = @Schema(implementation = Polnilnica.class))
            ),
            @APIResponse(responseCode = "422",
                    description = "Polnilnica - Validacijska napaka"
            )
    })
    @POST//MALONE
    @BeleziKlice
    public Response ustvariPolnilnico(@RequestBody(
            description = "DTO objekt za dodajanje polnilnice",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Najem.class))) Polnilnica polnilnica){
        return Response.status(Response.Status.OK).entity(p.ustvariPolnilnico(polnilnica)).build();
    }


    @Operation(description = "Posodobi Polnilnico.", summary = "Posodobitev polnilnice")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Polnilnica uspešno posodobljena",
                    content = @Content(schema = @Schema(implementation = Polnilnica.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Polnilnica Not Found"
            )
    })
    @PUT
    @BeleziKlice
    @Path("{id}")
    public Response posodobiPolnilnico(@PathParam("id") int id, @RequestBody(
            description = "DTO objekt za urejanje polnilnice",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Najem.class))) Polnilnica polnilnica){
        Polnilnica temp = p.posodobiPolnilnico(polnilnica,id);
        if(temp == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(temp).build();
    }

    @Operation(description = "Izbriši polnilnico.", summary = "Izbris Polnilnice")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Polnilnica uspešno odstranjena",
                    content = @Content(schema = @Schema(type = SchemaType.BOOLEAN))
            ),
            @APIResponse(responseCode = "404",
                    description = "Polnilnica Not Found"
            )
    })
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
