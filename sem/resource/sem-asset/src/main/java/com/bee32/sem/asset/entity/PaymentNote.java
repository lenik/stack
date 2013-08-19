package com.bee32.sem.asset.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DefaultValue;

import com.bee32.icsf.principal.User;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.process.state.util.StateInt;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

/**
 * 付款单
 *
 * 企业支付款项时填制的单据。
 *
 * <p lang="en">
 */
@Entity
@DiscriminatorValue("PAY")
public class PaymentNote
        extends FundFlow
        implements IVerifiable<ISingleVerifierWithNumber> {

    private static final long serialVersionUID = 1L;
    public static final int APPROVE_MESSAGE_LENGTH = 200;

    Person whoPay;

    @Override
    protected void createTransients() {
        if (verifyContext == null)
            verifyContext = new SingleVerifierWithNumberSupport(this);
        verifyContext.bind(this);
    }

    /**
     * 付款人
     *
     * 一般指企业里的出纳。
     *
     * @return
     */
    @ManyToOne
    public Person getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(Person whoPay) {
        this.whoPay = whoPay;
    }

    SingleVerifierWithNumberSupport verifyContext;

    /**
     * 审核上下文
     *
     * 付款单需要审核，返回支持审核的上下文。
     */
    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupport verifyContext) {
        this.verifyContext = verifyContext;
        verifyContext.bind(this);
    }

    @StateInt
    public static final int STATE_INIT = _STATE_0;

    @StateInt
    public static final int STATE_APPROVED = _STATE_NORMAL_END;

    @StateInt
    public static final int STATE_REJECTED = _STATE_ABNORMAL_END;

    User approveUser;
    boolean approved;
    String approveMessage = "";

    @ManyToOne
    public User getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(User approveUser) {
        this.approveUser = approveUser;
    }

    @Column(nullable = false)
    @DefaultValue("false")
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Column(nullable = false, length = APPROVE_MESSAGE_LENGTH)
    @DefaultValue("''")
    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        if (approveMessage == null)
            throw new NullPointerException("approveMessage");
        this.approveMessage = approveMessage;
    }

    @Override
    protected Integer stateCode() {
        if (approveUser == null)
            return STATE_INIT;

        if (approved)
            return STATE_APPROVED;
        else
            return STATE_REJECTED;
    }
}
