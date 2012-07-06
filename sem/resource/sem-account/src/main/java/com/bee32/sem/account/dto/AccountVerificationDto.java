package com.bee32.sem.account.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.account.entity.AccountVerification;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class AccountVerificationDto
        extends ProcessEntityDto<AccountVerification> {

    private static final long serialVersionUID = 1L;

    ReceivableDto receivable;
    ReceivedDto received;
    MutableMCValue amount;

    public AccountVerificationDto() {
        super();
    }

    public AccountVerificationDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(AccountVerification source) {
        receivable = mref(ReceivableDto.class, source.getReceivable());
        received = mref(ReceivedDto.class, source.getReceived());
        amount = source.getAmount().toMutable();

    }

    @Override
    protected void _unmarshalTo(AccountVerification target) {
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
