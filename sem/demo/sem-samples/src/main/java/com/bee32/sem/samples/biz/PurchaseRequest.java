package com.bee32.sem.samples.biz;

import java.io.Serializable;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyState;
import com.bee32.sem.process.verify.impl.AllowList;
import com.bee32.sem.process.verify.impl.AllowState;
import com.bee32.sem.samples.user.Users;

public class PurchaseRequest
        implements IVerifiable, Serializable {

    private static final long serialVersionUID = 1L;

    private static final AllowList VERIFY_STRATEGY;

    static {
        VERIFY_STRATEGY = new AllowList(//
                Users.tom, Users.kate);
    }

    // unused:
    String product;
    int price;
    int quantity;

    private AllowState verifyData;

    public PurchaseRequest() {
        // verifyData = connection.query("select verifier from PurchaseVerifiers where ...");
    }

    public IPrincipal getVerifierPerson() {
        if (verifyData == null)
            return null;
        return verifyData.getAllowedBy();
    }

    public void setVerifierPerson(IPrincipal person) {
        if (person == null)
            this.verifyData = null;
        else
            this.verifyData = new AllowState(person);
    }

    public IVerifyPolicy getVerifyPolicy() {
        return VERIFY_STRATEGY;
    }

    @Override
    public VerifyState getVerifyState() {
        return verifyData;
    }

}