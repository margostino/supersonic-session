package org.gaussian.supersonic.session.service;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gaussian.supersonic.session.model.db.SessionDBItem;
import org.gaussian.supersonic.session.model.session.Session;

import javax.enterprise.context.Dependent;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;


@Slf4j
@Dependent
@AllArgsConstructor
public class SessionDatabaseService {

    private final DatabaseService databaseService;

    public Uni<Void> create(Session session) {
        return databaseService.put(session);
    }

    public void createAndForget(Session session) {
        // TODO: better event bus
        databaseService.put(session).subscribe().with(item -> log.info("created"), failure -> log.error("failed"));
    }

    public Uni<Optional<Session>> get(String sessionId) {
        return databaseService.get(sessionId).map(this::extractDataFrom);
    }

    public Uni<String> update(Session session) {
        return databaseService.update(SessionDBItem.fromDomain(session)).map(result -> session.etag());
    }

    public void updateAndForget(Session session) {
        // TODO: better event bus
        databaseService.update(SessionDBItem.fromDomain(session)).subscribe().with(item -> log.info("created"), failure -> log.error("failed"));
    }

    public Uni<Boolean> delete(String sessionId) {
        return databaseService.delete(sessionId).map(result -> true);
    }

    private Optional<Session> extractDataFrom(PaginatedQueryList<SessionDBItem> itemResponse) {
        if (itemResponse.size() > 0) {
            return of(SessionDBItem.fromDomain(itemResponse.get(0)));
        }
        return empty();
    }

}
