package org.gaussian.supersonic.session.serializer;

import io.vertx.core.json.JsonObject;
import org.apache.kafka.common.serialization.Deserializer;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class EventDeserializer implements Deserializer<JsonObject> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing
    }

    @Override
    public JsonObject deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        return null;
    }

    @Override
    public void close() {
        // nothing
    }

}