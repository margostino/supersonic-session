package org.gaussian.supersonic.session.service;

import org.gaussian.supersonic.session.exception.SessionServiceException;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class SessionFailureHandler {

    public static int getStatusCodeFrom(Throwable failure) {

        if (failure instanceof SessionServiceException) {
            return ((SessionServiceException) failure).status;
        }

        return SC_INTERNAL_SERVER_ERROR;
    }

}
