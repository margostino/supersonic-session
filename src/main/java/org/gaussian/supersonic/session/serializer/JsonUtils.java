package org.gaussian.supersonic.session.serializer;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUtils {

	private JsonUtils() {
	}

	public static List<JsonObject> toList(JsonArray array) {
		if (array != null) {
			List<JsonObject> schemaJsons = new ArrayList<>();
			for (int index = 0; index < array.size(); index++) {
				schemaJsons.add(array.getJsonObject(index));
			}
			return schemaJsons;
		} else {
			return Collections.emptyList();
		}
	}

}
