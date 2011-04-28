package com.bee32.plover.ajax;

public abstract class SuccessOrFailMessage
        extends SuccessOrFailure {

    public SuccessOrFailMessage() {
        super();
    }

    public SuccessOrFailMessage(String successMessage) {
        super(successMessage);
    }

    public SuccessOrFailMessage(Throwable exception) {
        super(exception);
    }

    @Override
    protected final void service()
            throws Exception {

        String message = eval();

        if (message == null)
            setResult(SUCCESS);
        else
            setResult(FAILURE);

        setMessage(message);
    }

    protected abstract String eval()
            throws Exception;

}
