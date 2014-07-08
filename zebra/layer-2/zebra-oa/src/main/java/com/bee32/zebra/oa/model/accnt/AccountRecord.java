package com.bee32.zebra.oa.model.accnt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.bodz.bas.meta.decl.Redundant;

import com.bee32.zebra.oa.model.contact.Party;
import com.tinylily.model.base.CoMomentInterval;

public class AccountRecord
        extends CoMomentInterval {

    private static final long serialVersionUID = 1L;

    private Party party;
    private List<AccountLine> lines = new ArrayList<>();
    private Set<String> tags = new TreeSet<String>();

    private double debitTotal;
    private double creditTotal;

    public synchronized void update() {
        debitTotal = 0;
        creditTotal = 0;
        for (AccountLine line : lines) {
            if (line.isDebitSide())
                debitTotal += line.getValue();
            if (line.isCreditSide())
                creditTotal += line.getValue();
        }
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public List<AccountLine> getLines() {
        return lines;
    }

    public void setLines(List<AccountLine> lines) {
        this.lines = lines;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Redundant
    public double getDebitTotal() {
        return debitTotal;
    }

    public void setDebitTotal(double debitTotal) {
        this.debitTotal = debitTotal;
    }

    @Redundant
    public double getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(double creditTotal) {
        this.creditTotal = creditTotal;
    }

}
