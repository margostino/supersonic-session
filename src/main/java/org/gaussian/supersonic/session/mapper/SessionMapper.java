package org.gaussian.supersonic.session.mapper;

import lombok.extern.slf4j.Slf4j;
import org.gaussian.supersonic.session.exception.SessionNotFoundException;
import org.gaussian.supersonic.session.model.request.CreateSessionRequest;
import org.gaussian.supersonic.session.model.request.UpdateSessionRequest;
import org.gaussian.supersonic.session.model.response.SessionResponse;
import org.gaussian.supersonic.session.model.session.Session;
import org.gaussian.supersonic.session.service.IdGenerator;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.gaussian.supersonic.session.model.session.SessionStatus.INCOMPLETE;

@Slf4j
@Dependent
public class SessionMapper {

    @Inject
    private IdGenerator idGenerator;

    public SessionResponse fromDomain(Optional<Session> session, String sessionId) {
        return session.map(this::fromDomain).orElseThrow(() -> new SessionNotFoundException(sessionId));
    }

    public SessionResponse fromDomain(Session session) {
        return SessionResponse.builder()
                              .sessionId(session.getSessionId())
                              .createdAt(session.getCreatedAt())
                              .expiresAt(session.getExpiresAt())
                              .country(session.getCountry())
                              .tasks(session.getTasks())
                              .build();
    }

    public Session toDomain(CreateSessionRequest request) {
        String sessionId = idGenerator.createId();
        Instant now = now();
        Session session = Session.builder()
                                 .sessionId(sessionId)
                                 .country(request.country)
                                 .sessionStatus(INCOMPLETE)
                                 .createdAt(now)
                                 .expiresAt(now.plus(1, HOURS))
                                 .lastModifiedAt(now)
                                 .tasks(request.tasks)
                                 .build();
        return session;
    }

    public Session toDomain(String sessionId, UpdateSessionRequest request) {
        return Session.builder()
                      .sessionId(sessionId)
                      .tasks(request.getTasks())
                      .build();
    }

}
