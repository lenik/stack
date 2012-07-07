package com.bee32.sem.account;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.account.entity.AccountPayVerification;
import com.bee32.sem.account.entity.AccountRecePay;
import com.bee32.sem.account.entity.AccountReceVerification;
import com.bee32.sem.account.entity.NotesReceivable;
import com.bee32.sem.account.entity.Paied;
import com.bee32.sem.account.entity.Payable;
import com.bee32.sem.account.entity.Receivable;
import com.bee32.sem.account.entity.Received;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Resource, SEMOids.resource.Account })
public class SEMAccountModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/3/5";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(AccountPayVerification.class, "pay-verification");
        declareEntityPages(AccountRecePay.class, "account-rece-pay");
        declareEntityPages(AccountReceVerification.class, "rece-verification");
        declareEntityPages(NotesReceivable.class, "notes-receivable");
        declareEntityPages(Paied.class, "paied");
        declareEntityPages(Payable.class, "payable");
        declareEntityPages(Receivable.class, "receivable");
        declareEntityPages(Received.class, "received");
    }

}
