package org.gaussian.supersonic.session.model.session;

public enum SessionStatus {
    INCOMPLETE, COMPLETE;

    public String toString() {
        return name().toLowerCase();
    }
}
