package api.v1.viri;


import anotacije.BeleziKlice;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.kumuluz.ee.security.annotations.Secure;
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
import zrna.NajemZrno;
import zrna.UporabnikZrno;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
@Secure
public class UporabnikVir {

    @Inject
    private UporabnikZrno up;


    @Context
    protected UriInfo uriInfo;

    private static Logger log = Logger.getLogger(UporabnikVir.class.getName());

    @Operation(description = "Pridobi vse uporabnike.", summary = "Vračanje uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List uporabnikov",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih uporabnikov")}
            ),
            @APIResponse(responseCode = "404",
                    description = "Uporabnik Not Found"
            )
    })
    @GET
    @PermitAll
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

    @Operation(description = "Pridobi uporabnika.", summary = "Vračanje uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Uporabnik uspešno najden",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Uporabnik Not Found"
            )
    })
    @Path("{id}")
    @GET
    @RolesAllowed("user")
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

    @Operation(description = "Ustvari uporabnika.", summary = "Kreiranje uporabnikov")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Uporabnik uspešno dodan",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            ),
            @APIResponse(responseCode = "422",
                    description = "Uporabnik - Validacijska napaka"
            )
    })
    @POST//MALONE
    @PermitAll
    public Response ustvariUporabnika(@RequestBody(
            description = "DTO objekt za dodajanje uporabnika",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Najem.class))) Uporabnik uporabnik){
        return Response.status(Response.Status.OK).entity(up.ustvariUporabnika(uporabnik)).build();
    }


    @Operation(description = "Posodobi Uporabnika.", summary = "Posodobitev uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Uporabnik uspešno posodobljen",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Uporabnik Not Found"
            )
    })
    @PUT
    @Path("{id}")
    @PermitAll
    public Response posodobiUporabnika(@PathParam("id") int id, @RequestBody(
            description = "DTO objekt za urejanje uporabnika",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Najem.class))) Uporabnik uporabnik){
        Uporabnik temp = up.posodobiUporabnika(uporabnik,id);
        if(temp == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(temp).build();
    }


    @Operation(description = "Izbriši uporabnika.", summary = "Izbris uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Uporabnik uspešno odstranjen",
                    content = @Content(schema = @Schema(type = SchemaType.BOOLEAN))
            ),
            @APIResponse(responseCode = "404",
                 description = "Uporabnik not found"
            )
    })
    @DELETE
    @RolesAllowed("admin")
    @Path("{id}")
    public Response odstraniUporabnika(@PathParam("id") int id){
        boolean temp = up.odstraniUporabnika(id);
        if(temp == false){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();

    }

}
















