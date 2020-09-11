package org.gaussian.supersonic.session.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Optional;

public class EmptyStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        return Optional.ofNullable(parser.getText())
                       .map(String::trim)
                       .filter(value -> !value.isEmpty())
                       .orElse(null);
    }
}