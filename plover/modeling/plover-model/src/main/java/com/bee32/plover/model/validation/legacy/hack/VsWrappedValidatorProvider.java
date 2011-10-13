package com.bee32.plover.model.validation.legacy.hack;

import org.hibernate.validator.HibernateValidator;

public class VsWrappedValidatorProvider
        extends HibernateValidator {

    static void makeChangeToBuiltins() {

    }

    static {
        makeChangeToBuiltins();
    }

}
