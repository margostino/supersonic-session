package org.gaussian.supersonic.session.service;

import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gaussian.supersonic.session.mapper.SessionMapper;
import org.gaussian.supersonic.session.model.HttpRequestProperties;
import org.gaussian.supersonic.session.model.request.CreateSessionRequest;
import org.gaussian.supersonic.session.model.request.UpdateSessionRequest;
import org.gaussian.supersonic.session.model.response.SessionResponse;
import org.gaussian.supersonic.session.model.session.Session;
import org.gaussian.supersonic.session.validation.CreateSessionChecks;
import org.gaussian.supersonic.session.validation.RequestValidator;

import javax.enterprise.context.Dependent;

@Slf4j
@Dependent
@AllArgsConstructor
public class SessionService {

    private final SessionMapper sessionMapper;
    private final TrackingService trackingService;
    private final RequestValidator requestValidator;
    private final SessionDatabaseService sessionDatabaseService;

    public Uni<SessionResponse> create(CreateSessionRequest request, HttpRequestProperties headers) {
        requestValidator.validateRequest(request, CreateSessionChecks.class);
        Session session = sessionMapper.toDomain(request);
        return create(session, headers).map(sessionMapper::fromDomain)
                                       .map(trackingService::track);
    }

    public Uni<SessionResponse> get(String sessionId) {
        return sessionDatabaseService.get(sessionId)
                                     .map(session -> sessionMapper.fromDomain(session, sessionId));
    }

    public Uni<String> update(String sessionId, UpdateSessionRequest request) {
        Session updatedSession = sessionMapper.toDomain(sessionId, request);
        return sessionDatabaseService.update(updatedSession);
    }

    public Uni<Boolean> delete(String sessionId) {
        return sessionDatabaseService.delete(sessionId);
    }

    private Uni<Session> create(Session session, HttpRequestProperties headers) {
        return headers.isFireAndForget() ? processCreateAndForgetRequest(session) : processCreateRequest(session);
    }

    private Uni<Session> processCreateRequest(Session session) {
        return sessionDatabaseService.create(session)
                                     .map(result -> session);
    }

    private Uni<Session> processCreateAndForgetRequest(Session session) {
        sessionDatabaseService.createAndForget(session);
        return Uni.createFrom().item(session);
    }

}
