package org.gaussian.supersonic.session.model.db.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.Instant;

public class InstantConverter implements DynamoDBTypeConverter<String, Instant> {

    @Override
    public String convert(Instant instant) {
        return instant.toString();
    }

    @Override
    public Instant unconvert(String instant) {
        return Instant.parse(instant);
    }

}
