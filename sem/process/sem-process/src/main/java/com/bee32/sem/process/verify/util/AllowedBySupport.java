package com.bee32.sem.process.verify.util;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.process.verify.IAllowedByContext;

/**
 * Verifiable entity support class
 *
 * @param C
 *            The verify context class. You must implement the context in the same object.
 */
@MappedSuperclass
public abstract class AllowedBySupport<K extends Serializable, C extends IAllowedByContext>
        extends VerifiableEntity<K, C>
        implements IAllowedByContext {

    private static final long serialVersionUID = 1L;

    private User verifier;
    private Date verifiedDate;
    private boolean allowed;
    private String rejectedReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @Override
    public User getVerifier() {
        return verifier;
    }

    public void setVerifier(User verifier) {
        this.verifier = verifier;
    }

    @Override
    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Column(length = 80)
    @Override
    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    @Temporal(TemporalType.TIMESTAMP)
    // @Column(name = "tv")
    public Date getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Date verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

}
