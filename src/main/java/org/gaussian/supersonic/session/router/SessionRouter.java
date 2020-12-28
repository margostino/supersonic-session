package org.gaussian.supersonic.session.router;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.gaussian.supersonic.session.model.HttpRequestProperties;
import org.gaussian.supersonic.session.model.request.CreateSessionRequest;
import org.gaussian.supersonic.session.model.request.UpdateSessionRequest;
import org.gaussian.supersonic.session.model.response.SessionResponse;
import org.gaussian.supersonic.session.service.SessionService;

import javax.inject.Inject;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;
import static io.vertx.core.http.HttpMethod.DELETE;
import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.eclipse.microprofile.metrics.MetricUnits.MILLISECONDS;
import static org.gaussian.supersonic.session.serializer.JsonCodec.decode;
import static org.gaussian.supersonic.session.serializer.JsonCodec.encode;
import static org.gaussian.supersonic.session.service.SessionFailureHandler.getStatusCodeFrom;

// TODO: add ETag support for Session Optimistic Locking

@Slf4j
@RouteBase(path = "api")
public class SessionRouter {

    @Inject
    private SessionService sessionService;

    @APIResponse(responseCode = "200",
                 description = "Create a new session.",
                 content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = SessionResponse.class)))
    @APIResponse(responseCode = "400", description = "We were unable to create a session with the provided data. Some field constraint was violated.")
    @APIResponse(responseCode = "403", description = "You were not authorized to execute this operation.")
    @Route(path = "/sessions", produces = APPLICATION_JSON, methods = POST)
    public void create(RoutingContext context) {
        HttpServerRequest httpRequest = context.request();
        final HttpRequestProperties headers = HttpRequestProperties.fromRequest(httpRequest);
        CreateSessionRequest request = decode(context.getBodyAsString(), CreateSessionRequest.class);
        HttpServerResponse httpServerResponse = context.response().putHeader(CONTENT_TYPE, APPLICATION_JSON);

        sessionService.create(request, headers)
                      .subscribe()
                      .with(item -> handleResponse(item, httpServerResponse), failure -> handleResponse(failure, httpServerResponse));
    }

    @APIResponse(responseCode = "200",
                 description = "Read an existing session.",
                 content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CreateSessionRequest.class)))
    @APIResponse(responseCode = "403", description = "You were not authorized to execute this operation.")
    @APIResponse(responseCode = "404", description = "The session does not exist.")
    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MILLISECONDS)
    @Route(path = "/sessions/:session_id", produces = APPLICATION_JSON, methods = GET)
    public void get(RoutingContext context) {
        HttpServerRequest httpRequest = context.request();
        final String sessionId = httpRequest.getParam("session_id");
        HttpServerResponse httpServerResponse = context.response().putHeader(CONTENT_TYPE, APPLICATION_JSON);

        sessionService.get(sessionId).subscribe()
                      .with(item -> handleResponse(item, httpServerResponse), failure -> handleResponse(failure, httpServerResponse));
    }

    @APIResponse(responseCode = "204", description = "The session was updated successfully.")
    @APIResponse(responseCode = "400", description = "We were unable to update the session with the provided data. Some field constraint was violated.")
    @APIResponse(responseCode = "403", description = "You were not authorized to execute this operation.")
    @APIResponse(responseCode = "404", description = "The session does not exist.")
    @Route(path = "/sessions/:session_id", methods = POST)
    public void update(RoutingContext context) {
        HttpServerRequest httpRequest = context.request();
        final String sessionId = httpRequest.getParam("session_id");
        UpdateSessionRequest request = decode(context.getBodyAsString(), UpdateSessionRequest.class);
        HttpServerResponse httpServerResponse = context.response();

        sessionService.update(sessionId, request)
                      .subscribe()
                      .with(etag -> handleResponse(etag, httpServerResponse), failure -> handleResponse(failure, httpServerResponse));
    }

    @APIResponse(responseCode = "200", description = "Delete an existing session.")
    @APIResponse(responseCode = "403", description = "You were not authorized to execute this operation.")
    @APIResponse(responseCode = "404", description = "The session does not exist.")
    @Route(path = "/sessions/:session_id", methods = DELETE)
    public void delete(RoutingContext context) {
        HttpServerRequest httpRequest = context.request();
        final String sessionId = httpRequest.getParam("session_id");
        HttpServerResponse httpServerResponse = context.response();

        sessionService.delete(sessionId)
                      .subscribe()
                      .with(result -> handleResponse(httpServerResponse), failure -> handleResponse(failure, httpServerResponse));
    }

    private void handleResponse(SessionResponse response, HttpServerResponse httpServerResponse) {
        httpServerResponse.setStatusCode(200)
                          .putHeader("ETag", response.getEtag())
                          .end(encode(response));
    }

    private void handleResponse(String etag, HttpServerResponse httpServerResponse) {
        httpServerResponse.setStatusCode(204)
                          .putHeader("ETag", etag)
                          .end();
    }

    private void handleResponse(HttpServerResponse httpServerResponse) {
        httpServerResponse.setStatusCode(204)
                          .end();
    }

    private void handleResponse(Throwable throwable, HttpServerResponse httpServerResponse) {
        JsonObject response = new JsonObject().put("message", throwable.getMessage());
        httpServerResponse.setStatusCode(getStatusCodeFrom(throwable))
                          .end(response.encode());
    }

}
