package com.bee32.sem.account.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.account.entity.AccountPayVerification;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class AccountPayVerificationDto
        extends ProcessEntityDto<AccountPayVerification> {

    private static final long serialVersionUID = 1L;

    PayableDto payable;
    PaiedDto paied;
    MutableMCValue amount;

    public AccountPayVerificationDto() {
        super();
    }

    public AccountPayVerificationDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(AccountPayVerification source) {
        payable = mref(PayableDto.class, source.getPayable());
        paied = mref(PaiedDto.class, source.getPaied());
        amount = source.getAmount().toMutable();

    }

    @Override
    protected void _unmarshalTo(AccountPayVerification target) {
        merge(target, "payable", payable);
        merge(target, "paied", paied);
        target.setAmount(amount);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public PayableDto getPayable() {
        return payable;
    }

    public void setPayable(PayableDto payable) {
        this.payable = payable;
    }

    public PaiedDto getPaied() {
        return paied;
    }

    public void setPaied(PaiedDto paied) {
        this.paied = paied;
    }

    public MutableMCValue getAmount() {
        return amount;
    }

    public void setAmount(MutableMCValue amount) {
        this.amount = amount;
    }
}
