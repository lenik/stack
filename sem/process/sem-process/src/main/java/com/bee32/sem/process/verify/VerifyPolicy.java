package com.bee32.sem.process.verify;

import javax.free.DecodeException;
import javax.free.EncodeException;
import javax.free.XMLs;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.hibernate.annotations.AccessType;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.result.ErrorResult;
import com.bee32.sem.process.verify.result.PendingResult;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "stereo", length = 4)
public abstract class VerifyPolicy<C, S extends VerifyState>
        extends EntityBean<Integer>
        implements IVerifyPolicy<C, S> {

    private static final long serialVersionUID = 1L;

    private final Class<S> verifyStateClass;

    /**
     * Initialize the policy name from the class name.
     */
    public VerifyPolicy(Class<S> verifyStateClass) {
        if (verifyStateClass == null)
            throw new NullPointerException("verifyStateClass");
        this.verifyStateClass = verifyStateClass;
    }

    @AccessType("field")
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This is a shortcut to {@link ContextClassUtil#getContextClass(Class)}.
     *
     * @return The required context class for this verify policy, non-<code>null</code>.
     */
    @Transient
    public Class<?> getContextClass() {
        Class<?> contextClass = ContextClassUtil.getContextClass(getClass());
        return contextClass;
    }

    @Override
    public String encodeState(C context, S state)
            throws EncodeException {
        return XMLs.encode(state);
    }

    @Override
    public S decodeState(C context, String stateClob)
            throws DecodeException {
        Object object = XMLs.decode(stateClob);
        S state = verifyStateClass.cast(object);
        return state;
    }

    @Transient
    protected ErrorResult getNullResult() {
        return PendingResult.getInstance();
    }

    public ErrorResult validateState(C context, S state) {
        return null;
    }

    /**
     * 检查审核状态，如果未审核给出审核失败的消息。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @param state
     *            审核状态，非 <code>null</code> 值。
     * @return <code>null</code> means verified, otherwise the error message.
     */
    public abstract ErrorResult checkState(C context, S state);

    ErrorResult _verify(C context, S state) {

        if (state == null)
            return getNullResult();

        // policy-consistency validation.
        ErrorResult errorResult = validateState(context, state);

        // Continue to check if the state is validated.
        if (errorResult == null)
            errorResult = checkState(context, state);

        return errorResult;
    }

    public void verify(C context, S state)
            throws VerifyException {

        ErrorResult errorResult = _verify(context, state);

        if (errorResult != null)
            throw new VerifyException(String.valueOf(errorResult));
    }

    public boolean isVerified(C context, S state) {
        ErrorResult errorResult = _verify(context, state);
        return errorResult == null;
    }

}
