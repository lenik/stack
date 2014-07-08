package com.bee32.zebra.oa.model.contact;

import java.util.ArrayList;
import java.util.List;

public class Organization
        extends Party {

    private static final long serialVersionUID = 1L;

    private int size;
    private String taxId;
    private List<Person> staff = new ArrayList<>();
    private List<OrgUnit> topLevels = new ArrayList<OrgUnit>();

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * NSRSBH
     */
    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public List<Person> getStaff() {
        return staff;
    }

    public List<OrgUnit> getTopLevels() {
        return topLevels;
    }

}
