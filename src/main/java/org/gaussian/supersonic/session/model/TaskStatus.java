package org.gaussian.supersonic.session.model;

public enum TaskStatus {
    INCOMPLETE, COMPLETE;

    public String toString() {
        return name().toLowerCase();
    }
}
