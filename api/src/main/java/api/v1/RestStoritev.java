package api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@DeclareRoles({"user", "admin"})
@ApplicationPath("v1")
@OpenAPIDefinition(
        info = @Info(
        title = "PRPO polnilne postaje REST",
        version = "v1.0.0",
        contact = @Contact(email = "rok.kondic@gmail.com"),
        license = @License(name="dev")),
        servers = @Server(url = "http://localhost:8080/"))
public class RestStoritev extends Application {
}
