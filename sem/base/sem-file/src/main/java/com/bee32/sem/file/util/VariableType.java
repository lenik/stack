package com.bee32.sem.file.util;

import javax.free.UnexpectedException;

public enum VariableType {

    ENV,

    PROPERTY,

    CONST,

    ;

    public String expand(String variable) {
        if (variable == null)
            throw new NullPointerException("variable");

        switch (this) {
        case ENV:
            String env = System.getenv(variable);
            if (env == null)
                throw new IllegalArgumentException("Environ variable " + variable + " isn't defined.");
            return env;

        case PROPERTY:
            String property = System.getProperty(variable);
            if (property == null)
                throw new IllegalArgumentException("System property " + variable + " isn't defined");
            return property;

        case CONST:
            return variable;
        }
        throw new UnexpectedException("Bad variable type: " + this);
    }

}
