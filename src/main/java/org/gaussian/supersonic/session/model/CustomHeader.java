package org.gaussian.supersonic.session.model;

public enum CustomHeader {
    FIRE_AND_FORGET("Fire-And-Forget");

    public final String key;

    CustomHeader(String headerKey) {
        this.key = headerKey;
    }
}