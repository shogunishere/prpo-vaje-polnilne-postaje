package api.v1.mappers;

import izjeme.NeveljavenNajemDtoIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NeveljavenNajemDtoExceprionMapper implements
        ExceptionMapper<NeveljavenNajemDtoIzjema>{
    @Override
    public Response toResponse(NeveljavenNajemDtoIzjema exception){
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\":\"" + exception.getMessage() + "\"}")
                .build();
    }
}
