package com.bee32.plover.arch;

public class BuildException
        extends Exception {

    private static final long serialVersionUID = 1L;

    public BuildException() {
        super();
    }

    public BuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildException(String message) {
        super(message);
    }

    public BuildException(Throwable cause) {
        super(cause);
    }

}
