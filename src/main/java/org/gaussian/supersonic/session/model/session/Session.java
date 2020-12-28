package org.gaussian.supersonic.session.model.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import org.gaussian.supersonic.session.model.Task;

import java.time.Instant;
import java.util.List;

@Getter
@JsonDeserialize(builder = Session.SessionBuilder.class)
public class Session {

    public static final String SESSION_ID_URL_PLACEHOLDER = "{session.id}";

    private final String sessionId;
    private final SessionStatus sessionStatus;
    private final String country;
    private final Instant createdAt;
    private final Instant lastModifiedAt;
    private final Instant expiresAt;
    private final List<Task> tasks;

    @Builder(toBuilder = true)
    private Session(@JsonProperty("session_id") String sessionId,
                    @JsonProperty("country") String country,
                    @JsonProperty("session_status") SessionStatus sessionStatus,
                    @JsonProperty("created_at") Instant createdAt,
                    @JsonProperty("expired_at") Instant expiresAt,
                    @JsonProperty("last_modified_at") Instant lastModifiedAt,
                    @JsonProperty("tasks") List<Task> tasks) {
        this.sessionId = sessionId;
        this.sessionStatus = sessionStatus;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.tasks = tasks;
        this.country = country;
        this.expiresAt = expiresAt;
    }

    @Override
    public int hashCode() {
        return sessionId.hashCode() * sessionStatus.hashCode() * country.hashCode() * lastModifiedAt.hashCode();// * tasks.hashCode();
    }

    public String etag() {
        return String.valueOf(hashCode());
    }
}