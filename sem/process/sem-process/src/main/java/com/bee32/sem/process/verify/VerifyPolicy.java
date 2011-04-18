package com.bee32.sem.process.verify;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.result.ErrorResult;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
public abstract class VerifyPolicy<C extends IVerifyContext>
        extends EntityBean<Integer>
        implements IVerifyPolicy<C> {

    private static final long serialVersionUID = 1L;

    private String description;

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

    public ErrorResult validate(C context) {
        return null;
    }

    /**
     * 检查审核状态，如果未审核给出审核失败的消息。
     *
     * @param context
     *            当前审核状态的上下文对象（通常是审核数据所属的 Entity）。
     * @return <code>null</code> means verified, otherwise the error message.
     */
    public abstract ErrorResult check(C context);

    ErrorResult _verify(C context) {

        // policy-consistency validation.
        ErrorResult errorResult = validate(context);

        // Continue to check if the state is validated.
        if (errorResult == null)
            errorResult = check(context);

        return errorResult;
    }

    public void verify(C context)
            throws VerifyException {

        ErrorResult errorResult = _verify(context);

        if (errorResult != null)
            throw new VerifyException(String.valueOf(errorResult));
    }

    public boolean isVerified(C context) {
        ErrorResult errorResult = _verify(context);
        return errorResult == null;
    }

}
