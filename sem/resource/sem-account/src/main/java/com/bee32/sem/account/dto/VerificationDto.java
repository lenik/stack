package com.bee32.sem.account.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.account.entity.Verification;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class VerificationDto
        extends ProcessEntityDto<Verification> {

    private static final long serialVersionUID = 1L;

    AccountAbleDto accountAble;
    AccountEdDto accountEd;
    MutableMCValue amount;

    public VerificationDto() {
        super();
    }

    public VerificationDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(Verification source) {
        accountAble = mref(AccountAbleDto.class, source.getAccontAble());
        accountEd = mref(AccountEdDto.class, source.getAccountEd());
        amount = source.getAmount().toMutable();

    }

    @Override
    protected void _unmarshalTo(Verification target) {
        merge(target, "accountAble", accountAble);
        merge(target, "accountEd", accountEd);
        target.setAmount(amount);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public AccountAbleDto getAccountAble() {
        return accountAble;
    }

    public void setAccountAble(AccountAbleDto accountAble) {
        this.accountAble = accountAble;
    }

    public AccountEdDto getAccountEd() {
        return accountEd;
    }

    public void setAccountEd(AccountEdDto accountEd) {
        this.accountEd = accountEd;
    }

    public MutableMCValue getAmount() {
        return amount;
    }

    public void setAmount(MutableMCValue amount) {
        this.amount = amount;
    }
}
