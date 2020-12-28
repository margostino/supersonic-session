package org.gaussian.supersonic.session.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import org.gaussian.supersonic.session.model.Task;
import org.gaussian.supersonic.session.model.session.SessionStatus;

import java.time.Instant;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

@JsonInclude(NON_ABSENT)
@Getter
@Builder
@JsonDeserialize(builder = SessionResponse.SessionResponseBuilder.class)
public class SessionResponse {

    public final String sessionId;
    public final Instant createdAt;
    public final Instant expiresAt;
    public final String country;
    private final SessionStatus sessionStatus;
    private final List<Task> tasks;
    @JsonIgnore
    private final String etag;

    public SessionResponse(String sessionId, Instant createdAt, Instant expiresAt, String country, SessionStatus sessionStatus, List<Task> tasks, String etag) {
        this.sessionId = sessionId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.country = country;
        this.sessionStatus = sessionStatus;
        this.tasks = tasks;
        this.etag = etag;
    }
}
