package com.bee32.sem.process.verify.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.process.verify.ISingleVerifier;

/**
 * Verifiable entity support class
 *
 * @param C
 *            The verify context class. You must implement the context in the same object.
 */
@MappedSuperclass
public abstract class SingleVerifierSupport<C extends ISingleVerifier>
        extends AbstractVerifyContext<C>
        implements ISingleVerifier {

    private static final long serialVersionUID = 1L;

    private User verifier1;
    private Date verifiedDate1;
    private Boolean accepted1;
    private String rejectedReason1;

    public SingleVerifierSupport(Entity<?> entity) {
        super(entity);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Override
    public User getVerifier1() {
        return verifier1;
    }

    public void setVerifier1(User verifier) {
        this.verifier1 = verifier;
    }

    @Override
    public Boolean isAccepted1() {
        return accepted1;
    }

    public void setAccepted1(boolean accepted1) {
        this.accepted1 = accepted1;
    }

    @Column(length = 80)
    @Override
    public String getRejectedReason1() {
        return rejectedReason1;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    @Temporal(TemporalType.TIMESTAMP)
    // @Column(name = "tv")
    public Date getVerifiedDate1() {
        return verifiedDate1;
    }

    public void setVerifiedDate(Date verifiedDate1) {
        this.verifiedDate1 = verifiedDate1;
    }

}
