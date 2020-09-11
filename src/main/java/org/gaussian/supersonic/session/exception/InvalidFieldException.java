package org.gaussian.supersonic.session.exception;

import com.google.common.collect.Lists;

import java.util.List;

import static java.util.stream.Collectors.joining;


public class InvalidFieldException extends InvalidOperationException {

    private final List<String> fieldNames;

    public InvalidFieldException(String fieldName) {
        this(Lists.newArrayList(fieldName));
    }

    public InvalidFieldException(List<String> fieldNames) {
        super(toLogMessage(fieldNames));
        this.fieldNames = fieldNames;
    }

    private static String toLogMessage(List<String> fieldNames) {
        String invalidFields = fieldNames.stream().collect(joining(", "));
        return "Bad value: " + invalidFields;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }
}
