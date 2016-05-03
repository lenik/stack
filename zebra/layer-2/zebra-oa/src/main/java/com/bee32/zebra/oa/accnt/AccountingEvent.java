package com.bee32.zebra.oa.accnt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.meta.cache.Statistics;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.base.SchemaPref;
import net.bodz.lily.model.mx.base.CoMessage;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.sys.Schemas;

/**
 * 凭证单
 */
@IdType(Long.class)
@SchemaPref(value = Schemas.ACCOUNTING, form = 801)
@Table(name = "acdoc")
public class AccountingEvent
        extends CoMessage<Long> {

    private static final long serialVersionUID = 1L;

    private AccountingEvent previous;
    private Topic topic;
    private Organization org;
    private Person person;
    private double value;

    private List<AccountingEntry> entries = new ArrayList<>();
    private double debitTotal;
    private double creditTotal;

    @Override
    public void instantiate() {
        super.instantiate();
        setAccessMode(M_SHARED);
    }

    public synchronized void update() {
        BigDecimal debitTotal = BigDecimal.ZERO;
        BigDecimal creditTotal = BigDecimal.ZERO;
        for (AccountingEntry entry : entries) {
            if (entry.getSide() == DebitOrCredit.DEBIT)
                debitTotal = debitTotal.add(entry.getAbsValue());
            else
                creditTotal = creditTotal.add(entry.getAbsValue());
        }
    }

    /**
     * 前级
     */
    @DetailLevel(DetailLevel.HIDDEN)
    @OfGroup(StdGroup.Process.class)
    public AccountingEvent getPrevious() {
        return previous;
    }

    public void setPrevious(AccountingEvent previous) {
        this.previous = previous;
    }

    /**
     * 项目
     * 
     * 凭证记账下的功能相当于会计科目。
     */
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    /**
     * 公司
     * 
     * 凭证记账下的功能相当于会计科目。
     */
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * 联系人
     * 
     * 凭证记账下的功能相当于会计科目。
     */
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 金额
     * 
     * 凭证上的字面金额，通常和凭证正文上的有效总金额相等，为后续决策的提供依据。
     */
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    /**
     * 明细
     */
    @DetailLevel(DetailLevel.EXTEND)
    public List<AccountingEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<AccountingEntry> entries) {
        this.entries = entries;
    }

    /**
     * （总）借
     * 
     * 凭证记账下的借方总金额。通常数值上应该和<code>（总）贷</code>相等。
     */
    @OfGroup(StdGroup.Statistics.class)
    @Statistics
    public double getDebitTotal() {
        return debitTotal;
    }

    public void setDebitTotal(double debitTotal) {
        this.debitTotal = debitTotal;
    }

    /**
     * （总）贷
     * 
     * 凭证记账下的贷方总金额。通常数值上应该和<code>（总）借</code>相等。
     */
    @OfGroup(StdGroup.Statistics.class)
    @Statistics
    public double getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(double creditTotal) {
        this.creditTotal = creditTotal;
    }

}
