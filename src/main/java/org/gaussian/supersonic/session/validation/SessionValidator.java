package org.gaussian.supersonic.session.validation;


import org.gaussian.supersonic.session.model.session.Session;

public abstract class SessionValidator {

    private SessionValidator next;

    public SessionValidator linkWith(SessionValidator next) {
        this.next = next;
        return next;
    }

    public abstract void validate(Session session);

    protected void checkNext(Session session) {
        if(next != null) {
            next.validate(session);
        }
    }
}
