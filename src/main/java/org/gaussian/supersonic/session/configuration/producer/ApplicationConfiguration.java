package org.gaussian.supersonic.session.configuration.producer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.gaussian.supersonic.session.qualifiers.Environment;
import org.gaussian.supersonic.session.serializer.mapping.VertxJsonModule;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@ApplicationScoped
public class ApplicationConfiguration {

    @Produces
    @Singleton
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .setSerializationInclusion(JsonInclude.Include.NON_DEFAULT)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
                .configure(WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(new Jdk8Module())
                .registerModule(new VertxJsonModule())
                .registerModule(new JavaTimeModule());
    }

    @Produces
    @Singleton
    @Environment
    public String environment() {
        return "local";
    }

}
