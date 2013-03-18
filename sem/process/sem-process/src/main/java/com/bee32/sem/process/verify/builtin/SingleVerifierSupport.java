package com.bee32.sem.process.verify.builtin;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DefaultValue;

import com.bee32.icsf.principal.Principal;
import com.bee32.sem.process.verify.AbstractVerifyContext;

/**
 * Verifiable entity support class
 *
 * @param C
 *            The verify context class. You must implement the context in the same object.
 */
@MappedSuperclass
@Embeddable
public class SingleVerifierSupport
        extends AbstractVerifyContext
        implements ISingleVerifier {

    private static final long serialVersionUID = 1L;

    public static final int REJECTED_REASON_LENGTH = 80;

    Principal verifier1;
    Date verifiedDate1;
    boolean accepted1;
    String rejectedReason1;

    public SingleVerifierSupport() {
        super();
    }

    @Override
    public void populate(Object source) {
        if (source instanceof SingleVerifierSupport)
            _populate((SingleVerifierSupport) source);
        else
            super.populate(source);
    }

    protected void _populate(SingleVerifierSupport o) {
        super._populate(o);
        this.verifier1 = o.verifier1;
        this.verifiedDate1 = o.verifiedDate1;
        this.accepted1 = o.accepted1;
        this.rejectedReason1 = o.rejectedReason1;
    }

    /**
     * 审核人
     *
     * 审核操作的执行人。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Override
    public Principal getVerifier1() {
        return verifier1;
    }

    public void setVerifier1(Principal verifier) {
        this.verifier1 = verifier;
    }

    /**
     * 审核日期
     *
     * 审核操作的日期。
     */
    @Temporal(TemporalType.TIMESTAMP)
    // @Column(name = "tv")
    @Override
    public Date getVerifiedDate1() {
        return verifiedDate1;
    }

    public void setVerifiedDate1(Date verifiedDate1) {
        this.verifiedDate1 = verifiedDate1;
    }

    /**
     * 审核通过
     *
     * 审核的决策值，如 未审核/允许/拒绝。
     */
    @Column(nullable = false)
    @DefaultValue("false")
    @Override
    public boolean isAccepted1() {
        return accepted1;
    }

    public void setAccepted1(boolean accepted1) {
        this.accepted1 = accepted1;
    }

    /**
     * 拒绝原因
     *
     * 审核责任人填写的附加消息。
     */
    @Column(length = REJECTED_REASON_LENGTH)
    @Override
    public String getRejectedReason1() {
        return rejectedReason1;
    }

    public void setRejectedReason1(String rejectedReason1) {
        this.rejectedReason1 = rejectedReason1;
    }

}
