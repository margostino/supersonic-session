package org.gaussian.supersonic.session.validation;

import lombok.AllArgsConstructor;
import org.gaussian.supersonic.session.exception.EmptyRequestException;
import org.gaussian.supersonic.session.model.request.CreateSessionRequest;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Dependent
@AllArgsConstructor
public class RequestValidator {

    @Inject
    private Validator validator;

    public void validateRequest(CreateSessionRequest createSessionRequest, Class<?>... validationClass) {
        if (createSessionRequest == null) {
            throw new EmptyRequestException();
        }

        Set<ConstraintViolation<Object>> validationErrors = validator.validate(createSessionRequest, validationClass);
        if (!validationErrors.isEmpty()) {
            throw new ConstraintViolationException("Request validation failed", validationErrors);
        }
    }
}
