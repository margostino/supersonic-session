package org.gaussian.supersonic.session.model.db.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import org.gaussian.supersonic.session.model.session.SessionStatus;

public class SessionStatusConverter implements DynamoDBTypeConverter<String, SessionStatus> {

    @Override
    public String convert(SessionStatus sessionStatus) {
        return sessionStatus.name();
    }

    @Override
    public SessionStatus unconvert(String sessionStatus) {
        return SessionStatus.valueOf(sessionStatus);
    }

}
