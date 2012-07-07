package com.bee32.sem.account.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.account.entity.AccountReceVerification;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class AccountReceVerificationDto
        extends ProcessEntityDto<AccountReceVerification> {

    private static final long serialVersionUID = 1L;

    ReceivableDto receivable;
    ReceivedDto received;
    MutableMCValue amount;

    public AccountReceVerificationDto() {
        super();
    }

    public AccountReceVerificationDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(AccountReceVerification source) {
        receivable = mref(ReceivableDto.class, source.getReceivable());
        received = mref(ReceivedDto.class, source.getReceived());
        amount = source.getAmount().toMutable();

    }

    @Override
    protected void _unmarshalTo(AccountReceVerification target) {
        merge(target, "receivable", receivable);
        merge(target, "received", received);
        target.setAmount(amount);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

}
