package com.bee32.zebra.oa.contact;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.repr.form.meta.OfGroup;

import com.bee32.zebra.oa.OaGroups;

public class Organization
        extends Party {

    private static final long serialVersionUID = 1L;

    private int size;
    private String taxId;
    private List<Person> staff = new ArrayList<>();
    private List<OrgUnit> topLevels = new ArrayList<OrgUnit>();

    /**
     * 全名
     */
    @OfGroup(OaGroups.Identity.class)
    public String getFullName() {
        return getLabel();
    }

    public void setFullName(String fullName) {
        setLabel(fullName);
    }

    /**
     * 主营业务
     */
    @Override
    public String getSubject() {
        return super.getSubject();
    }

    /**
     * 规模
     * 
     * @format ###人
     */
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * 税号
     * 
     * @format ######-###-###-###
     */
    @OfGroup(OaGroups.Identity.class)
    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    /**
     * 人员表
     */
    public List<Person> getStaff() {
        return staff;
    }

    /**
     * 顶级机构
     */
    public List<OrgUnit> getTopLevels() {
        return topLevels;
    }

}
