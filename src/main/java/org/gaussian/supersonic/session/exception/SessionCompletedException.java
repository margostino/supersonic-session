package org.gaussian.supersonic.session.exception;

public class SessionCompletedException extends InvalidOperationException {

    public SessionCompletedException() {
        super("Session has already been completed");
    }
}
