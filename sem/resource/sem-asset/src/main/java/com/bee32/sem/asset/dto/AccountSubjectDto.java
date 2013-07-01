package com.bee32.sem.asset.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.dict.NameDictDto;
import com.bee32.sem.asset.entity.AccountSubject;

public class AccountSubjectDto
        extends NameDictDto<AccountSubject> {

    private static final long serialVersionUID = 1L;

    boolean debitSign;
    boolean creditSign;

    @Override
    protected void _marshal(AccountSubject source) {
        debitSign = source.isDebitSign();
        creditSign = source.isCreditSign();
    }

    @Override
    protected void _unmarshalTo(AccountSubject target) {
        target.setDebitSign(debitSign);
        target.setCreditSign(creditSign);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public boolean isDebitSign() {
        return debitSign;
    }

    public void setDebitSign(boolean debitSign) {
        this.debitSign = debitSign;
        this.creditSign = !debitSign;
    }

    public boolean isCreditSign() {
        return creditSign;
    }

    public void setCreditSign(boolean creditSign) {
        this.creditSign = creditSign;
        this.debitSign = !creditSign;
    }

}
