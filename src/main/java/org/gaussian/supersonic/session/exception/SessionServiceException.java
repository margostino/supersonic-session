package org.gaussian.supersonic.session.exception;

/**
 * Base class for unchecked exceptions thrown when an invalid operation is attempted.
 */
public abstract class SessionServiceException extends RuntimeException {

    public final int status;

    public SessionServiceException(String message, int status) {
        super(message);
        this.status = status;
    }

    public SessionServiceException(String message, int status, Exception cause) {
        super(message, cause);
        this.status = status;
    }
}
