package com.bee32.zebra.erp.stock.impl;

import java.util.Set;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;

import com.bee32.zebra.erp.stock.PlaceUsage;
import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.sea.QVariantMap;

public class CellCriteria
        extends CoEntityCriteria {

    PlaceUsage usage;
    DoubleRange volumeRange;
    Set<Integer> parties;
    Set<Integer> partyOrgs;

    public PlaceUsage getUsage() {
        return usage;
    }

    public void setUsage(PlaceUsage usage) {
        this.usage = usage;
    }

    public DoubleRange getVolumeRange() {
        return volumeRange;
    }

    public void setVolumeRange(DoubleRange volumeRange) {
        this.volumeRange = volumeRange;
    }

    public Set<Integer> getParties() {
        return parties;
    }

    public void setParties(Set<Integer> parties) {
        this.parties = parties;
    }

    public Set<Integer> getPartyOrgs() {
        return partyOrgs;
    }

    public void setPartyOrgs(Set<Integer> partyOrgs) {
        this.partyOrgs = partyOrgs;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        usage = map.getEnum(PlaceUsage.class, "usage", usage);
        volumeRange = map.getDoubleRange("volumes", volumeRange);
        parties = map.getInts("parties", parties);
        partyOrgs = map.getInts("orgs", partyOrgs);
    }

}
