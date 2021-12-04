package api.v1.viri;


import api.v1.mappers.NeveljavenNajemDtoExceprionMapper;
import com.kumuluz.ee.rest.beans.QueryParameters;
import dtos.NajemDTO;
import dtos.PolnilnicaDTO;
import izjeme.NeveljavenNajemDtoIzjema;
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
import zrna.NajemZrno;
import zrna.UpravljanjeNajemovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@Path("najemi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class NajemVir {

    @Inject
    private NajemZrno n;

    @Inject
    private UpravljanjeNajemovZrno up;

    @Context
    protected UriInfo uriInfo;

    private static Logger log = Logger.getLogger(NajemVir.class.getName());


    @Operation(description = "Pridobi vse najeme.", summary = "Vračanje najemov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List najemov",
                    content = @Content(schema = @Schema(implementation = Najem.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih najemob+v")}
            ),
            @APIResponse(responseCode = "404",
                    description = "Najemi Not Found"
            )
    })
    @GET
    public Response vrniNajeme(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Najem> najemi = n.pridobiVseNajeme(query);
        Long stVsehNajemov = n.getNajemCount(query);


        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count",stVsehNajemov)
                .entity(najemi)
                .build();
    }

    @Operation(description = "Pridobi Najem.", summary = "Vračanje najema")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Najem uspešno najden",
                    content = @Content(schema = @Schema(implementation = Najem.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Najem Not Found"
            )
    })
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

    @Operation(description = "Ustvari najem.", summary = "Kreiranje najema")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Najem uspešno dodan",
                    content = @Content(schema = @Schema(implementation = Najem.class))
            ),
            @APIResponse(responseCode = "422",
                    description = "Najem - Validacijska napaka"
            )
    })
    @POST
    public Response ustvariNajem(@RequestBody(
            description = "DTO objekt za odstranjevanje najema",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Najem.class))) Najem najem){
        return Response.status(Response.Status.OK).entity(n.ustvariNajem(najem)).build();
    }

    @Operation(description = "Posodobi Najem.", summary = "Posodobitev najema")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Najem uspešno posodobljena",
                    content = @Content(schema = @Schema(implementation = Najem.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Najem Not Found"
            )
    })
    @PUT
    @Path("{id}")
    public Response posodobiNajem(@RequestBody(
            description = "DTO objekt za odstranjevanje najema",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Najem.class))) Najem najem,@PathParam("id") int id){
        Najem temp = n.posodobiNajem(najem,id);
        if(temp == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(temp).build();
    }

    @Operation(description = "Izbriši najem.", summary = "Izbris Najema")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Najem uspešno odstranjen",
                    content = @Content(schema = @Schema(type = SchemaType.BOOLEAN))
            ),
            @APIResponse(responseCode = "404",
                    description = "Najem Not Found"
            )
    })
    @DELETE
    @Path("{id}")
    public Response odstraniNajem(@PathParam("id") int id){
        boolean temp = n.odstraniNajem(id);
        if(temp == false){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(temp).build();
    }

    @Operation(description = "Rezerviraj najem.", summary = "Rezervacija najema")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Najem rezerviran",
                    content = @Content(schema = @Schema(implementation = Najem.class))
            ),
            @APIResponse(responseCode = "422",
                    description = "Najem - Validacijska napaka"
            )
    })
    @POST
    @Path("rezerviraj_najem")
    public Response narediRezervacijo(@RequestBody(
            description = "DTO objekt za rezervacijo",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Najem.class))) Najem najem){
        NajemDTO n = new NajemDTO();
        n.setTermin(najem.getTermin());
        n.setUporabnik_najema(najem.getUporabnik());
        n.setPolnilnica_najema(najem.getPolnilnica());

        Najem novNajem = null;

        try {
            novNajem = up.rezervacijaNajema(n);
        }
        catch (NeveljavenNajemDtoIzjema e){
            NeveljavenNajemDtoExceprionMapper m = new NeveljavenNajemDtoExceprionMapper();
            return m.toResponse(e);
        }
        return Response.status(Response.Status.OK).entity(novNajem).build();
    }

    @Operation(description = "Prekliči rezervacijo najema.", summary = "Preklic Najema")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Rezervacija najema uspešno odstranjena",
                    content = @Content(schema = @Schema(type = SchemaType.BOOLEAN))
            ),
            @APIResponse(responseCode = "404",
                    description = "Najem Not Found"
            )
    })
    @DELETE
    @Path("preklici_najem")
    public Response prekliciNajem(@RequestBody(
            description = "DTO objekt za preklic rezervacije",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Najem.class))) Najem najem){

        NajemDTO n = new NajemDTO();
        n.setTermin(najem.getTermin());
        n.setUporabnik_najema(najem.getUporabnik());
        n.setPolnilnica_najema(najem.getPolnilnica());

        try {
            up.prekliciNajem(n);
        }
        catch (NeveljavenNajemDtoIzjema e){
            NeveljavenNajemDtoExceprionMapper m = new NeveljavenNajemDtoExceprionMapper();
            return m.toResponse(e);
        }
        return Response.status(Response.Status.OK).entity("Preklic Uspešen").build();
    }

    @Operation(description = "Izračunaj ceno najema.", summary = "Izračun cene najema")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Cena uspešno izračunana",
                    content = @Content(schema = @Schema(type = SchemaType.NUMBER))
            ),
            @APIResponse(responseCode = "404",
                    description = "Najem Not Found"
            )
    })
    @GET
    @Path("izracunaj")
    public Response izracunCeneNajema(@RequestBody(
            description = "DTO objekt za izračun polnjenja",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Najem.class))) Najem najem){

        NajemDTO n = new NajemDTO();
        n.setTermin(najem.getTermin());
        n.setUporabnik_najema(najem.getUporabnik());
        n.setPolnilnica_najema(najem.getPolnilnica());

        PolnilnicaDTO p = new PolnilnicaDTO();
        p.setCena(najem.getPolnilnica().getCena());

        return Response.status(Response.Status.OK).entity(up.izracunCenePolnjenja(najem.getTermin(),p)).build();
    }

}
