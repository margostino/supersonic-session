package org.gaussian.supersonic.session.exception;

public class DatabaseOperationException extends InvalidOperationException {

    public DatabaseOperationException(Exception e) {
        super(e.getMessage());
    }

}
