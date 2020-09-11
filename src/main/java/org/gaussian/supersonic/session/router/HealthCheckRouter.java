package org.gaussian.supersonic.session.router;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.vertx.ext.web.RoutingContext;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;
import static io.vertx.core.http.HttpMethod.GET;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * Router implementing a simple health check endpoint.
 * <p>
 * Clients can verify the endpoint is alive by sending a GET request to ./ping.
 */
@RouteBase(path = "reactive")
public class HealthCheckRouter {

    @Route(path = "/ping", produces = TEXT_PLAIN, methods = GET)
    @APIResponse(responseCode="200",
                 description="Health Check")
    public void ping(RoutingContext context) {
        context.response()
               .putHeader(CONTENT_TYPE, TEXT_PLAIN)
               .end("pong");
    }

}
