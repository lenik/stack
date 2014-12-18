package com.bee32.zebra.oa.accnt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.meta.cache.Derived;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;
import com.tinylily.model.mx.base.CoMessage;

public class AccountingEvent
        extends CoMessage {

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
            if (entry.isDebitSide())
                debitTotal = debitTotal.add(entry.getValue());
            if (entry.isCreditSide())
                creditTotal = creditTotal.add(entry.getValue());
        }
    }

    /**
     * 前级
     */
    public AccountingEvent getPrevious() {
        return previous;
    }

    public void setPrevious(AccountingEvent previous) {
        this.previous = previous;
    }

    /**
     * 项目
     */
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    /**
     * 公司
     */
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * 人员
     */
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<AccountingEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<AccountingEntry> entries) {
        this.entries = entries;
    }

    /**
     * 借（总）
     */
    @Derived(cached = true)
    public double getDebitTotal() {
        return debitTotal;
    }

    public void setDebitTotal(double debitTotal) {
        this.debitTotal = debitTotal;
    }

    /**
     * 贷（总）
     */
    @Derived(cached = true)
    public double getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(double creditTotal) {
        this.creditTotal = creditTotal;
    }

}
