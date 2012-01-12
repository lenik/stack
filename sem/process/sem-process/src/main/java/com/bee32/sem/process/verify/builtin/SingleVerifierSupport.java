package com.bee32.sem.process.verify.builtin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    private Principal verifier1;
    private Date verifiedDate1;
    private boolean accepted1;
    private String rejectedReason1;

    public SingleVerifierSupport() {
        super();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Override
    public Principal getVerifier1() {
        return verifier1;
    }

    public void setVerifier1(Principal verifier) {
        this.verifier1 = verifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    // @Column(name = "tv")
    @Override
    public Date getVerifiedDate1() {
        return verifiedDate1;
    }

    public void setVerifiedDate1(Date verifiedDate1) {
        this.verifiedDate1 = verifiedDate1;
    }

    @Column(nullable = false)
    @DefaultValue("false")
    @Override
    public boolean isAccepted1() {
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

    public void setRejectedReason1(String rejectedReason1) {
        this.rejectedReason1 = rejectedReason1;
    }

}
