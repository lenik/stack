package com.bee32.sem.process.verify;

import javax.free.IllegalUsageException;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.ox1.color.UIEntityAuto;

@ServiceTemplate(prototype = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "verify_policy_seq", allocationSize = 1)
public abstract class VerifyPolicy
        extends UIEntityAuto<Integer>
        implements IVerifyPolicy {

    private static final long serialVersionUID = 1L;

    protected static final VerifyResult UNKNOWN = new VerifyResult(VerifyState.UNKNOWN, null);
    protected static final VerifyResult VERIFIED = new VerifyResult(VerifyState.VERIFIED, null);

    private final Class<? extends IVerifyContext> contextClass;

    public VerifyPolicy(Class<? extends IVerifyContext> contextClass) {
        if (contextClass == null)
            throw new NullPointerException("contextClass");
        this.contextClass = contextClass;
    }

    @Transient
    @Override
    public Class<? extends IVerifyContext> getRequiredContext() {
        return contextClass;
    }

    protected <C extends IVerifyContext> C requireContext(Class<C> contextClass, IVerifyContext context) {
        if (contextClass == null)
            throw new NullPointerException("contextClass");

        if (contextClass.equals(this.contextClass))
            throw new IllegalUsageException("Require a different context from the defined one: " + contextClass);

        return contextClass.cast(context);
    }

    public boolean isUsefulFor(Class<? extends IVerifyContext> providedContext) {
        if (providedContext == null)
            throw new NullPointerException("providedContext");
        return contextClass.isAssignableFrom(providedContext);
    }

    @Override
    public final void assertVerified(IVerifyContext context)
            throws VerifyException {
        VerifyResult errorResult = verify(context);
        if (!errorResult.isVerified())
            throw new VerifyException(String.valueOf(errorResult));
    }

    @Override
    public final boolean isVerified(IVerifyContext context) {
        VerifyResult result = verify(context);
        return result.isVerified();
    }

    @Override
    public VerifyResult verify(IVerifyContext context) {

        // policy-consistency validation.
        VerifyResult result = validate(context);

        // Continue to check if the state is validated.
        if (result.isVerified())
            result = evaluate(context);

        return result;
    }

    /**
     * 检查审核状态，如果未审核给出审核失败的消息。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @return {@link #VERIFIED} means verified, otherwise the error message.
     */
    public VerifyResult validate(IVerifyContext context) {
        return VERIFIED;
    }

    /**
     * 计算审核状态，如果未审核给出审核失败的消息。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @return {@link #VERIFIED} means verified, otherwise the error message.
     */
    public abstract VerifyResult evaluate(IVerifyContext context);

}
