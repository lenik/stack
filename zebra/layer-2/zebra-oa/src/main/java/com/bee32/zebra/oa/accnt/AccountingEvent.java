package com.bee32.zebra.oa.accnt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.bodz.bas.meta.cache.Derived;

import com.bee32.zebra.oa.contact.Party;
import com.tinylily.model.base.CoMomentInterval;

public class AccountingEvent
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    private Party party;
    private List<AccountingEntry> entries = new ArrayList<>();
    private Set<String> tags = new TreeSet<String>();

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

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public List<AccountingEntry> getLines() {
        return entries;
    }

    public void setLines(List<AccountingEntry> lines) {
        this.entries = lines;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Derived(cached = true)
    public double getDebitTotal() {
        return debitTotal;
    }

    public void setDebitTotal(double debitTotal) {
        this.debitTotal = debitTotal;
    }

    @Derived(cached = true)
    public double getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(double creditTotal) {
        this.creditTotal = creditTotal;
    }

}
