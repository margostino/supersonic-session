package org.gaussian.supersonic.session.exception;

import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class SessionNotFoundException extends InvalidOperationException {

    public SessionNotFoundException(String sessionId) {
        super(format("Session ID %s not found", sessionId), SC_NOT_FOUND);
    }
}
