package org.gaussian.supersonic.session.service;

import io.vertx.mutiny.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.gaussian.supersonic.session.model.response.SessionResponse;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import static java.lang.String.format;
import static org.gaussian.supersonic.session.serializer.JsonCodec.encode;

@Slf4j
@Dependent
public class TrackingService {

    @Inject
    private EventBus bus;
    @ConfigProperty(name = "event-bus.session.create")
    private String eventAddress;

    public SessionResponse track(SessionResponse event) {
        log.info(format("[EVENTBUS/PUBLISH] New session %s with expiration date %s", event.sessionId, event.expiresAt.toString()));
        bus.publish(eventAddress, encode(event));
        return event;
    }
}
