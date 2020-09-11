package org.gaussian.supersonic.session.model.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.Setter;
import org.gaussian.supersonic.session.model.Task;
import org.gaussian.supersonic.session.model.db.converter.InstantConverter;
import org.gaussian.supersonic.session.model.db.converter.SessionStatusConverter;
import org.gaussian.supersonic.session.model.db.converter.TaskListConverter;
import org.gaussian.supersonic.session.model.session.Session;
import org.gaussian.supersonic.session.model.session.SessionStatus;

import java.time.Instant;
import java.util.List;

@Setter
@DynamoDBTable(tableName = "supersonic-sessions")
public class SessionDBItem {

    private String sessionId;
    private SessionStatus sessionStatus;
    private Instant createdAt;
    private Instant lastModifiedAt;
    private String country;
    private Instant expiresAt;
    private List<Task> tasks;

    @DynamoDBHashKey(attributeName = "session_id")
    public String getSessionId() {
        return sessionId;
    }

    @DynamoDBTypeConverted(converter = SessionStatusConverter.class)
    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    @DynamoDBTypeConverted(converter = InstantConverter.class)
    public Instant getCreatedAt() {
        return createdAt;
    }

    @DynamoDBTypeConverted(converter = InstantConverter.class)
    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }

    @DynamoDBTypeConverted(converter = TaskListConverter.class)
    public List<Task> getTasks() {
        return tasks;
    }

    @DynamoDBAttribute(attributeName = "country")
    public String getCountry() {
        return country;
    }

    @DynamoDBTypeConverted(converter = InstantConverter.class)
    public Instant getExpiresAt() {
        return expiresAt;
    }

    public static SessionDBItem fromDomain(Session session) {
        SessionDBItem sessionDBItem = new SessionDBItem();
        sessionDBItem.setSessionId(session.getSessionId());
        sessionDBItem.setCreatedAt(session.getCreatedAt());
        sessionDBItem.setLastModifiedAt(session.getLastModifiedAt());
        sessionDBItem.setSessionStatus(session.getSessionStatus());
        sessionDBItem.setTasks(session.getTasks());
        sessionDBItem.setCountry(session.getCountry());
        sessionDBItem.setExpiresAt(session.getExpiresAt());
        return sessionDBItem;
    }

    public static SessionDBItem fromDomain(String sessionId) {
        SessionDBItem sessionDBItem = new SessionDBItem();
        sessionDBItem.setSessionId(sessionId);
        return sessionDBItem;
    }

    public static Session fromDomain(SessionDBItem sessionDBItem) {
        return Session.builder()
                      .sessionId(sessionDBItem.getSessionId())
                      .createdAt(sessionDBItem.getCreatedAt())
                      .lastModifiedAt(sessionDBItem.getLastModifiedAt())
                      .sessionStatus(sessionDBItem.getSessionStatus())
                      .country(sessionDBItem.getCountry())
                      .expiresAt(sessionDBItem.getExpiresAt())
                      .tasks(sessionDBItem.getTasks())
                      .build();
    }

}