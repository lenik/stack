package com.bee32.zebra.oa.accnt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.db.meta.TableName;
import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.meta.cache.Statistics;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;
import com.tinylily.model.base.IdType;
import com.tinylily.model.mx.base.CoMessage;

/**
 * 凭证单
 */
@IdType(Long.class)
@TableName("acdoc")
public class AccountingEvent
        extends CoMessage<Long> {

    private static final long serialVersionUID = 1L;

    private AccountingEvent previous;
    private Topic topic;
    private Organization org;
    private Person person;

    private List<AccountingEntry> entries = new ArrayList<>();
    private double debitTotal;
    private double creditTotal;

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
