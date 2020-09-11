package org.gaussian.supersonic.session.exception;

import static java.lang.String.format;

public class EmptyRequestException extends InvalidOperationException {

    public EmptyRequestException() {
        super(format("Empty Request"));
    }
}
