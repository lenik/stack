package com.bee32.sem.account;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.account.entity.AccountAble;
import com.bee32.sem.account.entity.AccountEd;
import com.bee32.sem.account.entity.CurrentAccount;
import com.bee32.sem.account.entity.Note;
import com.bee32.sem.account.entity.NotePayable;
import com.bee32.sem.account.entity.NoteReceivable;
import com.bee32.sem.account.entity.Paid;
import com.bee32.sem.account.entity.Payable;
import com.bee32.sem.account.entity.PayableInit;
import com.bee32.sem.account.entity.Receivable;
import com.bee32.sem.account.entity.ReceivableInit;
import com.bee32.sem.account.entity.Received;
import com.bee32.sem.account.entity.Verification;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Resource, SEMOids.resource.Account })
public class SEMAccountModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/3/5";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(AccountAble.class, "account-able");
        declareEntityPages(AccountEd.class, "account-ed");
        declareEntityPages(CurrentAccount.class, "current-account");
        declareEntityPages(Note.class, "note");
        declareEntityPages(NotePayable.class, "note-payable");
        declareEntityPages(NoteReceivable.class, "note-receivable");
        declareEntityPages(Paid.class, "paid");
        declareEntityPages(Payable.class, "payable");
        declareEntityPages(PayableInit.class, "payable-init");
        declareEntityPages(Receivable.class, "receivable");
        declareEntityPages(ReceivableInit.class, "receivable-init");
        declareEntityPages(Received.class, "received");
        declareEntityPages(Verification.class, "verification");
    }

}
