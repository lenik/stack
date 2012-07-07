package com.bee32.sem.account;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.account.entity.AccountPayVerification;
import com.bee32.sem.account.entity.AccountRecePay;
import com.bee32.sem.account.entity.AccountReceVerification;
import com.bee32.sem.account.entity.BillType;
import com.bee32.sem.account.entity.NotesReceivable;
import com.bee32.sem.account.entity.Paied;
import com.bee32.sem.account.entity.Payable;
import com.bee32.sem.account.entity.Receivable;
import com.bee32.sem.account.entity.Received;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.world.SEMWorldUnit;

@ImportUnit({ SEMInventoryUnit.class, SEMPeopleUnit.class, SEMWorldUnit.class })
public class SEMAccountUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AccountPayVerification.class);
        add(AccountRecePay.class);
        add(AccountReceVerification.class);
        add(BillType.class);
        add(NotesReceivable.class);
        add(Paied.class);
        add(Payable.class);
        add(Receivable.class);
        add(Received.class);
    }
}
