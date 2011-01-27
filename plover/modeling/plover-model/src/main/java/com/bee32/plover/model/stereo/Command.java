package com.bee32.plover.model.stereo;

//@StereoConstruction
public abstract class Command
        extends StereoResolution {

    private Object[] parameters;
    private Object returnedValue;
    private Throwable thrownException;

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object... parameters) {
        if (parameters == null)
            throw new NullPointerException("parameters");
        this.parameters = parameters;
    }

    public Object getReturnedValue() {
        return returnedValue;
    }

    public Throwable getThrownException() {
        return thrownException;
    }

    /**
     * @return <code>true</code> If the command successfully executed, <code>false</code> else.
     */
    public abstract boolean execute();

}
