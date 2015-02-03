package com.bee32.zebra.oa.accnt;

import java.math.BigDecimal;

import net.bodz.bas.db.meta.TableName;
import net.bodz.bas.meta.cache.Derived;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;

import com.bee32.zebra.oa.OaGroups;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.CoMomentInterval;
import com.tinylily.model.base.IdType;

@IdType(Long.class)
@TableName("acentry")
public class AccountingEntry
        extends CoMomentInterval<Long> {

    private static final long serialVersionUID = 1L;

    public static final int DEBIT_SIDE = 0;
    public static final int CREDIT_SIDE = 1;

    AccountingEvent event;
    Account account;
    Organization org;
    Person person;

    boolean debitSide;
    BigDecimal value = BigDecimal.ZERO;

    /**
     * 凭证单
     */
    public AccountingEvent getEvent() {
        return event;
    }

    public void setEvent(AccountingEvent event) {
        this.event = event;
    }

    /**
     * 科目
     */
    @OfGroup(StdGroup.Classification.class)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * 公司
     */
    @OfGroup(StdGroup.Classification.class)
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * 联系人
     */
    @OfGroup(StdGroup.Classification.class)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 借方
     */
    @OfGroup(OaGroups.Accounting.class)
    @Derived
    public boolean isDebitSide() {
        return value.signum() == 1;
    }

    /**
     * 贷方
     */
    @Derived
    public boolean isCreditSide() {
        return !isDebitSide();
    }

    /**
     * 金额
     */
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
    }

}
