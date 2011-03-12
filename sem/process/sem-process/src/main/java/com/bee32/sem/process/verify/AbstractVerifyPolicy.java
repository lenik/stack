package com.bee32.sem.process.verify;

import javax.free.DecodeException;
import javax.free.EncodeException;
import javax.free.XMLs;

import com.bee32.plover.orm.entity.EntityBean;

public abstract class AbstractVerifyPolicy<C, S extends VerifyState>
        extends EntityBean<Integer>
        implements IVerifyPolicy<C, S> {

    private static final long serialVersionUID = 1L;

    private final Class<S> verifyStateClass;

    /**
     * Initialize the policy name from the class name.
     */
    public AbstractVerifyPolicy(Class<S> verifyStateClass) {
        if (verifyStateClass == null)
            throw new NullPointerException("verifyStateClass");
        this.verifyStateClass = verifyStateClass;
    }

    @Override
    public String encodeState(S state)
            throws EncodeException {
        return XMLs.encode(state);
    }

    @Override
    public S decodeState(String stateClob)
            throws DecodeException {
        Object object = XMLs.decode(stateClob);
        S state = verifyStateClass.cast(object);
        return state;
    }

    /**
     * Check if the given verify state is verified.
     *
     * @return <code>null</code> means verified, otherwise the error message.
     */
    public abstract String checkState(C context, S state);

    public void verify(C context, S state)
            throws VerifyException {
        String errorMessage = checkState(context, state);
        if (errorMessage != null)
            throw new VerifyException(errorMessage);
    }

    public boolean isVerified(C context, S state) {
        String errorMessage = checkState(context, state);
        return errorMessage == null;
    }

}
