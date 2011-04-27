package com.bee32.sem.process.verify;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.EntityBean;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
public abstract class VerifyPolicy<C extends IVerifyContext>
        extends EntityBean<Integer>
        implements IVerifyPolicy<C> {

    private static final long serialVersionUID = 1L;

    protected static final VerifyResult UNKNOWN = new VerifyResult(VerifyState.UNKNOWN, null);

    private String description;

    private final Class<C> contextClass;

    public VerifyPolicy() {
        contextClass = ClassUtil.infer1(getClass(), VerifyPolicy.class, 0);
    }

    @Column(length = 50)
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    @Override
    public Class<C> getRequiredContext() {
        return contextClass;
    }

    public boolean isUsefulFor(Class<? extends IVerifyContext> providedContext) {
        if (providedContext == null)
            throw new NullPointerException("providedContext");
        return contextClass.isAssignableFrom(providedContext);
    }

    @Override
    public final void assertVerified(C context)
            throws VerifyException {
        VerifyResult errorResult = verify(context);
        if (errorResult != null)
            throw new VerifyException(String.valueOf(errorResult));
    }

    @Override
    public final boolean isVerified(C context) {
        VerifyResult errorResult = verify(context);
        return errorResult == null;
    }

    @Override
    public VerifyResult verify(C context) {

        // policy-consistency validation.
        VerifyResult errorResult = validate(context);

        // Continue to check if the state is validated.
        if (errorResult == null)
            errorResult = evaluate(context);

        return errorResult;
    }

    /**
     * 检查审核状态，如果未审核给出审核失败的消息。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @return <code>null</code> means verified, otherwise the error message.
     */
    public VerifyResult validate(C context) {
        return null;
    }

    /**
     * 计算审核状态，如果未审核给出审核失败的消息。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @return <code>null</code> means verified, otherwise the error message.
     */
    public abstract VerifyResult evaluate(C context);

}
