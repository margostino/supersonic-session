package org.gaussian.supersonic.session.validation;

abstract public class ValidationRegex {

    public static final String LOCALE = "^[A-Za-z]{2}(?:-[A-Za-z]{2})*$";
    public static final String CURRENCY = "^[A-Za-z]{3}$";
    public static final String COUNTRY = "^[A-Za-z]{2}$";
}
