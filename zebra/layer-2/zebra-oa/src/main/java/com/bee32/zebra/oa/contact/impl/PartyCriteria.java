package com.bee32.zebra.oa.contact.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import net.bodz.bas.err.ParseException;
import net.bodz.bas.t.range.IntRange;

import com.tinylily.model.base.CoEntityCriteria;
import com.tinylily.model.sea.QVariantMap;

public class PartyCriteria
        extends CoEntityCriteria {

    public int type = PartyType.UNSET;
    IntRange ageRange;
    Boolean customer;
    Boolean supplier;
    Set<String> tags;

    public IntRange getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(IntRange ageRange) {
        this.ageRange = ageRange;
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
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        type = map.getInt("type", type);
        ageRange = map.getIntRange("ages", ageRange);
        customer = map.getBoolean("customer", customer);
        supplier = map.getBoolean("supplier", supplier);
        String tagsStr = map.getString("tags");
        if (tagsStr != null) {
            tags = new TreeSet<String>(Arrays.asList(tagsStr.split(",")));
        }
    }

}
