package com.bee32.sem.asset.dto;

import java.math.BigDecimal;
import java.util.Currency;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierWithNumberSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.world.monetary.MCValue;

public class AccountTicketItemDto
        extends TxEntityDto<AccountTicketItem>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport;

    int index;

    AccountSubjectDto subject;
    PartyDto party;

    MCValue value = new MCValue();

    boolean debitSide;
    AccountTicketDto ticket;

    @Override
    protected void _marshal(AccountTicketItem source) {
        index = source.getIndex();

        subject = mref(AccountSubjectDto.class, source.getSubject());
        party = mref(PartyDto.class, source.getParty());

        value = source.getValue();

        debitSide = source.isDebitSide();
        ticket = mref(AccountTicketDto.class, source.getTicket());
        singleVerifierWithNumberSupport = marshal(SingleVerifierWithNumberSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(AccountTicketItem target) {
        target.setIndex(index);

        merge(target, "subject", subject);
        merge(target, "party", party);

        target.setValue(value);

        target.setDebitSide(debitSide);
        merge(target, "ticket", ticket);
        merge(target, "verifyContext", singleVerifierWithNumberSupport);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public AccountSubjectDto getSubject() {
        return subject;
    }

    public void setSubject(AccountSubjectDto subject) {
        this.subject = subject;
    }

    public boolean isNegative() {
        if (subject == null) {
            throw new NullPointerException("subject");
        }

        if (debitSide) {
            //当前凭证条目为借方
            if (subject.debitSign) {
                //当前科目为"借方时为正数(增加)"
                return false;
            } else {
                //当前科目为"借方时为负数(减少)"
                return true;
            }
        } else {
            //当前凭证条目为贷方
            if (subject.creditSign) {
                //当前科目为"贷方时为正数(增加)"
                return false;
            } else {
                //当前科目为"贷方时为负数(减少)"
                return true;
            }
        }
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

    public MCValue getValue() {
        if (isNegative()) {
            return new MCValue(value.getCurrency(), value.getValue().negate());
        } else {
            return value;
        }
    }

    public void setValue(MCValue value) {
        if (value == null) {
            throw new NullPointerException("value");
        }
        if (isNegative()) {
            this.value = new MCValue(value.getCurrency(), value.getValue().negate());
        } else {
            this.value = value;
        }
    }

    public BigDecimal getValueDigit() {
        if (isNegative()) {
            return value.getValue().negate();
        } else {
            return value.getValue();
        }
    }

    public void setValueDigit(BigDecimal valueDigit) {
        if (valueDigit == null) {
            throw new NullPointerException("valueDigit");
        }
        if (isNegative()) {
            this.value = new MCValue(value.getCurrency(), valueDigit.negate());
        } else {
            this.value = new MCValue(value.getCurrency(), valueDigit);
        }
    }

    public String getValueCurrency() {
        if (value.getCurrency() == null)
            return CurrencyConfig.getNative().getCurrencyCode();
        else
            return value.getCurrency().getCurrencyCode();
    }

    public void setValueCurrency(String currencyCode) {
        value = new MCValue(Currency.getInstance(currencyCode), value.getValue());
    }

    public boolean isDebitSide() {
        return debitSide;
    }

    public void setDebitSide(boolean debitSide) {
        this.debitSide = debitSide;
    }

    public String getSideName() {
        if (debitSide)
            return "借方";
        else
            return "贷方";
    }

    public AccountTicketDto getTicket() {
        return ticket;
    }

    public void setTicket(AccountTicketDto ticket) {
        this.ticket = ticket;
    }

    public String getCreator() {
        return getOwner().getDisplayName();
    }

    @Override
    public SingleVerifierWithNumberSupportDto getVerifyContext() {
        return singleVerifierWithNumberSupport;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport) {
        this.singleVerifierWithNumberSupport = singleVerifierWithNumberSupport;
    }

}
