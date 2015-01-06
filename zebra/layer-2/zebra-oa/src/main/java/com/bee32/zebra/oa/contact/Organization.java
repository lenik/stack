package com.bee32.zebra.oa.contact;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.db.meta.TableName;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.meta.cache.Derived;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.bas.repr.form.meta.TextInput;

import com.tinylily.model.sea.QVariantMap;

@TableName("org")
public class Organization
        extends Party {

    private static final long serialVersionUID = 1L;

    public static final int N_TAXID = 20;

    private int size;
    private String taxId;

    private boolean shipper;

    private List<Person> staff = new ArrayList<>();
    private List<OrgUnit> topLevels = new ArrayList<OrgUnit>();

    /**
     * 全名
     */
    @TextInput(maxLength = N_LABEL)
    @OfGroup(StdGroup.Identity.class)
    public String getFullName() {
        return getLabel();
    }

    public void setFullName(String fullName) {
        setLabel(fullName);
    }

    /**
     * 主营业务
     * 
     * @placeholder 输入主营业务…
     */
    @TextInput(maxLength = N_SUBJECT)
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
     * 标记 - 承运人
     */
    public boolean isShipper() {
        return shipper;
    }

    public void setShipper(boolean shipper) {
        this.shipper = shipper;
    }

    /**
     * 税号
     * 
     * @placeholder 输入税号…
     * @format ######-###-###-###
     */
    @TextInput(maxLength = N_TAXID)
    @OfGroup(StdGroup.Identity.class)
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

    /**
     * TODO xjdoc don't inherit the docs from the super method.
     * 
     * 由一系列单字符描述的分类特征。
     * 
     * @label Characters
     * @label.zh 特征字
     */
    @OfGroup(StdGroup.Classification.class)
    @Derived
    @Override
    public String getTypeChars() {
        String typeChars = super.getTypeChars();
        if (shipper)
            typeChars += "运";
        return typeChars;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);

        size = map.getInt("size", size);
        taxId = map.getString("taxId");

        shipper = map.getBoolean("shipper", shipper);

        // staff = new ArrayList<>();
        // topLevels = new ArrayList<OrgUnit>();
    }

}
