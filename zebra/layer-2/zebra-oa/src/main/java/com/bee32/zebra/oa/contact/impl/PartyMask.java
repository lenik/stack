package com.bee32.zebra.oa.contact.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.IntRange;
import net.bodz.bas.t.variant.IVariantMap;
import net.bodz.bas.t.variant.QVariantMap;

import com.bee32.zebra.tk.sea.MyMask;

public class PartyMask
        extends MyMask {

    public int type = PartyType.CUSTOMER;
    IntRange ageRange;
    Boolean peer;
    Boolean customer;
    Boolean supplier;
    Set<String> tags;

    public IntRange getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(IntRange ageRange) {
        this.ageRange = ageRange;
    }

    public Boolean getPeer() {
        return peer;
    }

    public void setPeer(Boolean peer) {
        this.peer = peer;
    }

    public Boolean getCustomer() {
        return customer;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

    public Boolean getSupplier() {
        return supplier;
    }

    public void setSupplier(Boolean supplier) {
        this.supplier = supplier;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public void readObject(IVariantMap<String> _map)
            throws ParseException {
        super.readObject(_map);
        QVariantMap<String> map = QVariantMap.from(_map);
        type = map.getInt("type", type);
        ageRange = map.getIntRange("ages", ageRange);
        peer = map.getBoolean("peer", peer);
        customer = map.getBoolean("customer", customer);
        supplier = map.getBoolean("supplier", supplier);
        String tagsStr = map.getString("tags");
        if (tagsStr != null) {
            tags = new TreeSet<String>(Arrays.asList(tagsStr.split(",")));
        }
    }

}
