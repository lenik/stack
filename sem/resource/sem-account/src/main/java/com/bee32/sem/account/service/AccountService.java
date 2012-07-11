package com.bee32.sem.account.service;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.entity.PayableInit;
import com.bee32.sem.account.entity.ReceivableInit;
import com.bee32.sem.people.dto.PartyDto;

public class AccountService extends DataService implements IAccountService {

    @Override
    public boolean isReceivableInitExisted(PartyDto party) {

        ReceivableInit receivableInit =
                ctx.data.access(ReceivableInit.class)
                    .getFirst(new Equals("party.id", party.getId()));
        if(receivableInit == null)
            return false;

        return true;
    }

    @Override
    public boolean isPayableInitExisted(PartyDto party) {

        PayableInit payableInit =
                ctx.data.access(PayableInit.class)
                    .getFirst(new Equals("party.id", party.getId()));
        if(payableInit == null)
            return false;

        return true;
    }

}
