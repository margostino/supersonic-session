package org.gaussian.supersonic.session.exception;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;


/**
 * Base class for unchecked exceptions thrown when an invalid operation is attempted.
 */
public abstract class InvalidOperationException extends SessionServiceException {

    public InvalidOperationException(String message) {
        super(message, SC_FORBIDDEN);
    }

    public InvalidOperationException(String message, int status) {
        super(message, status);
    }

    public InvalidOperationException(String message, Exception cause) {
        super(message, SC_FORBIDDEN, cause);
    }
}
