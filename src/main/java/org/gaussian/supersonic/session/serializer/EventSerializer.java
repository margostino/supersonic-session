package org.gaussian.supersonic.session.serializer;

import io.vertx.core.json.JsonObject;
import org.apache.kafka.common.serialization.Serializer;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
public class EventSerializer implements Serializer<JsonObject> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing
    }

    @Override
    public byte[] serialize(String topic, JsonObject data) {
        if (data == null)
            return null;

        return data.encode().getBytes();
    }

    @Override
    public void close() {
        // nothing
    }

}