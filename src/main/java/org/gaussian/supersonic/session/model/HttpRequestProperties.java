package org.gaussian.supersonic.session.model;

import io.vertx.core.http.HttpServerRequest;
import lombok.Builder;
import lombok.Getter;

import static org.gaussian.supersonic.session.model.CustomHeader.FIRE_AND_FORGET;

@Getter
@Builder
public class HttpRequestProperties {

    private final boolean fireAndForget;

    public static HttpRequestProperties fromRequest(HttpServerRequest request) {
        return builder()
                .fireAndForget(Boolean.valueOf(request.getHeader(FIRE_AND_FORGET.key)))
                .build();
    }

}
