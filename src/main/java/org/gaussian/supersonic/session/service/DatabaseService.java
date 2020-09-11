package org.gaussian.supersonic.session.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.gaussian.supersonic.session.exception.DatabaseOperationException;
import org.gaussian.supersonic.session.exception.SessionNotFoundException;
import org.gaussian.supersonic.session.model.db.SessionDBItem;
import org.gaussian.supersonic.session.model.session.Session;
import org.gaussian.supersonic.session.qualifiers.SessionDBTableName;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@Dependent
public class DatabaseService {

    @Inject
    private DynamoDBMapper mapper;
    @Inject
    private @SessionDBTableName
    String tableName;

    public Uni<Void> put(Session session) {
        SessionDBItem sessionDBItem = SessionDBItem.fromDomain(session);
        Map<String, ExpectedAttributeValue> expected = getExistenceSaveExpression(session.getSessionId(), false);
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expected);

        try {
            return Uni.createFrom().completionStage(() -> runAsync(() -> mapper.save(sessionDBItem, saveExpression)));
        } catch (ConditionalCheckFailedException e) {
            log.error("Error: The table {} can't be found.", tableName);
            throw new DatabaseOperationException(e);
        } catch (ResourceNotFoundException e) {
            log.error("Error: The table {} can't be found.", tableName);
            throw new DatabaseOperationException(e);
        } catch (DynamoDbException e) {
            log.error(e.getMessage());
            throw new DatabaseOperationException(e);
        }
    }

    public Uni<Void> update(SessionDBItem sessionDBItem) {
        Map<String, ExpectedAttributeValue> expected = getExistenceSaveExpression(sessionDBItem.getSessionId(), true);
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expected);
        DynamoDBMapperConfig dynamoDBMapperConfig = new DynamoDBMapperConfig.Builder().withSaveBehavior(UPDATE_SKIP_NULL_ATTRIBUTES)
                                                                                      .build();

        try {
            return Uni.createFrom()
                      .completionStage(() -> runAsync(() -> mapper.save(sessionDBItem, saveExpression, dynamoDBMapperConfig)))
                      .onFailure()
                      .transform(failure -> handle(failure, sessionDBItem.getSessionId()));
        } catch (ResourceNotFoundException e) {
            log.error("Error: The table {} can't be found.", tableName);
            throw new DatabaseOperationException(e);
        } catch (DynamoDbException e) {
            log.error(e.getMessage());
            throw new DatabaseOperationException(e);
        }
    }

    public Uni<PaginatedQueryList<SessionDBItem>> get(String sessionId) {
        SessionDBItem sessionDBItem = SessionDBItem.fromDomain(sessionId);
        DynamoDBQueryExpression<SessionDBItem> query = new DynamoDBQueryExpression<SessionDBItem>().withHashKeyValues(sessionDBItem);
        try {
            return Uni.createFrom()
                      .completionStage(() -> supplyAsync(() -> mapper.query(SessionDBItem.class, query)));
        } catch (DynamoDbException e) {
            log.error(e.getMessage());
            throw new DatabaseOperationException(e);
        }
    }

    // TODO: enable idempotent operation
    public Uni<Void> delete(String sessionId) {
        SessionDBItem sessionDBItem = SessionDBItem.fromDomain(sessionId);
        try {
            return Uni.createFrom()
                      .completionStage(() -> runAsync(() -> mapper.delete(sessionDBItem)))
                      .onFailure()
                      .transform(failure -> handle(failure, sessionId));
        } catch (DynamoDbException e) {
            log.error(e.getMessage());
            throw new DatabaseOperationException(e);
        }
    }

    private Throwable handle(Throwable failure, String sessionId) {
        return (failure instanceof ConditionalCheckFailedException) ?
                new SessionNotFoundException(sessionId) : failure;
    }

    private Map<String, ExpectedAttributeValue> getExistenceSaveExpression(String sessionId, boolean itemExists) {
        Map expected = new HashMap();

        if (itemExists) {
            expected.put("session_id", new ExpectedAttributeValue().withExists(itemExists).withValue(new AttributeValue().withS(sessionId)));
        } else {
            expected.put("session_id", new ExpectedAttributeValue().withExists(itemExists));
        }

        return expected;
    }
}
