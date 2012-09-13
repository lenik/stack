package com.bee32.sem.asset.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.sem.people.entity.Person;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

/**
 * 付款单
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("PAY")
public class PaymentNote
    extends FundFlow
    implements IVerifiable<ISingleVerifierWithNumber> {

    private static final long serialVersionUID = 1L;
    Person whoPay;

    @Override
    protected void createTransients() {
        if (verifyContext == null)
            verifyContext = new SingleVerifierWithNumberSupport(this);
        verifyContext.bind(this);
    }

    @ManyToOne
    public Person getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(Person whoPay) {
        this.whoPay = whoPay;
    }

    SingleVerifierWithNumberSupport verifyContext;

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupport verifyContext) {
        this.verifyContext = verifyContext;
        verifyContext.bind(this);
    }
}
