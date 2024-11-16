package com.admin.catalog.domain.exceptions;

import com.admin.catalog.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;

    protected DomainException(final String aMessage, final List<Error> someErrors) {
        super(aMessage);
        this.errors = someErrors;
    }

    public static DomainException with(final Error anError) {
        return new DomainException(anError.message(), List.of(anError));
    }

    public static DomainException with(final List<Error> someErrors) {
        return new DomainException("", someErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
