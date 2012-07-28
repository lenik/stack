package com.bee32.sem.account;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.account.entity.AccountAble;
import com.bee32.sem.account.entity.AccountEd;
import com.bee32.sem.account.entity.Balancing;
import com.bee32.sem.account.entity.BillDiscount;
import com.bee32.sem.account.entity.BillType;
import com.bee32.sem.account.entity.CurrentAccount;
import com.bee32.sem.account.entity.Endorsement;
import com.bee32.sem.account.entity.Note;
import com.bee32.sem.account.entity.NoteBalancing;
import com.bee32.sem.account.entity.NotePayable;
import com.bee32.sem.account.entity.NoteReceivable;
import com.bee32.sem.account.entity.Paid;
import com.bee32.sem.account.entity.Payable;
import com.bee32.sem.account.entity.PayableInit;
import com.bee32.sem.account.entity.Receivable;
import com.bee32.sem.account.entity.ReceivableInit;
import com.bee32.sem.account.entity.Received;
import com.bee32.sem.account.entity.Verification;
import com.bee32.sem.account.entity.VerificationPay;
import com.bee32.sem.account.entity.VerificationRece;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.world.SEMWorldUnit;

@ImportUnit({ SEMInventoryUnit.class, SEMPeopleUnit.class, SEMWorldUnit.class })
public class SEMAccountUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AccountAble.class);
        add(AccountEd.class);
        add(BillType.class);
        add(CurrentAccount.class);
        add(Note.class);
        add(NotePayable.class);
        add(NoteReceivable.class);
        add(Paid.class);
        add(Payable.class);
        add(PayableInit.class);
        add(Receivable.class);
        add(ReceivableInit.class);
        add(Received.class);
        add(Verification.class);
        add(VerificationRece.class);
        add(VerificationPay.class);
        add(NoteBalancing.class);
        add(BillDiscount.class);
        add(Endorsement.class);
        add(Balancing.class);
    }
}
