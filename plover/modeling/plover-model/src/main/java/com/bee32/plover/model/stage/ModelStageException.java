package com.bee32.plover.model.stage;

import com.bee32.plover.model.ModelException;

public class ModelStageException
        extends ModelException {

    private static final long serialVersionUID = 1L;

    public ModelStageException() {
        super();
    }

    public ModelStageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelStageException(String message) {
        super(message);
    }

    public ModelStageException(Throwable cause) {
        super(cause);
    }

}
