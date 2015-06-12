package com.bee32.zebra.io.stock.impl;

import java.util.Set;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.DoubleRange;
import net.bodz.lily.model.sea.QVariantMap;

import com.bee32.zebra.io.stock.PlaceUsage;
import com.bee32.zebra.tk.sea.MyCriteria;

public class PlaceCriteria
        extends MyCriteria {

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
        usage = map.getPredef(PlaceUsage.class, "usage", usage);
        volumeRange = map.getDoubleRange("volumes", volumeRange);
        parties = map.getInts("parties", parties);
        partyOrgs = map.getInts("orgs", partyOrgs);
    }

}
