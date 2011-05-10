package com.bee32.sem.process.verify;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.GreenEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
public abstract class VerifyPolicy<C extends IVerifyContext>
        extends GreenEntity<Integer>
        implements IVerifyPolicy<C> {

    private static final long serialVersionUID = 1L;

    protected static final VerifyResult UNKNOWN = new VerifyResult(VerifyState.UNKNOWN, null);
    protected static final VerifyResult VERIFIED = new VerifyResult(VerifyState.VERIFIED, null);

    private final Class<C> contextClass;

    public VerifyPolicy() {
        contextClass = ClassUtil.infer1(getClass(), VerifyPolicy.class, 0);
    }

    @NaturalId(mutable = true)
    @Column(length = 50, unique = true)
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");

        if (name.isEmpty())
            throw new IllegalArgumentException("Name can't be empty");

        this.name = name;
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
        if (!errorResult.isVerified())
            throw new VerifyException(String.valueOf(errorResult));
    }

    @Override
    public final boolean isVerified(C context) {
        VerifyResult result = verify(context);
        return result.isVerified();
    }

    @Override
    public VerifyResult verify(C context) {

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
    public VerifyResult validate(C context) {
        return VERIFIED;
    }

    /**
     * 计算审核状态，如果未审核给出审核失败的消息。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @return {@link #VERIFIED} means verified, otherwise the error message.
     */
    public abstract VerifyResult evaluate(C context);

    @Override
    protected Boolean naturalEquals(EntityBase<Integer> other) {
        VerifyPolicy<?> o = (VerifyPolicy<?>) other;

        if (this.name == null || o.name == null)
            return false;

        return this.name.equals(o.name);
    }

    @Override
    protected Integer naturalHashCode() {
        return name == null ? 0 : name.hashCode();
    }

}
