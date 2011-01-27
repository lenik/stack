package com.bee32.plover.model.stage;

import com.bee32.plover.model.ModelException;

public class ModelLoadException
        extends ModelException {

    private static final long serialVersionUID = 1L;

    public ModelLoadException() {
        super();
    }

    public ModelLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelLoadException(String message) {
        super(message);
    }

    public ModelLoadException(Throwable cause) {
        super(cause);
    }

}
