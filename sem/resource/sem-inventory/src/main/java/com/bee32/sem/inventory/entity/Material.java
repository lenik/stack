package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CollectionOfElements;

import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.world.thing.Thing;

/**
 * 物料
 */
@Entity
public class Material
        extends Thing<MaterialXP> {

    private static final long serialVersionUID = 1L;

    MaterialCategory category;

    String serial;
    String barCode;

    List<MaterialAttribute> attributes = new ArrayList<MaterialAttribute>();
    List<UserFile> attachments = new ArrayList<UserFile>();

    List<MaterialWarehouseOption> options = new ArrayList<MaterialWarehouseOption>();
    List<MaterialPreferredLocation> preferredLocations = new ArrayList<MaterialPreferredLocation>();

    // List<MaterialPrice> prices = new ArrayList<MaterialPrice>();

    // ------------------------------------------------------------------------
    // 需要索引的常用的物料属性（这些属性和单位还算无关）。
    //
    // String packageSize;
    // String packageWeight;
    // ------------------------------------------------------------------------

    public Material() {
        super();
    }

    public Material(String name) {
        super(name);
    }

    public Material(String name, String serial) {
        super(name);
        if (serial == null)
            throw new NullPointerException("serial");
        this.serial = serial;
    }

    /**
     * 物料类别
     */
    @ManyToOne(optional = false)
    public MaterialCategory getCategory() {
        return category;
    }

    public void setCategory(MaterialCategory category) {
        if (category == null)
            throw new NullPointerException("category");
        this.category = category;
    }

    /**
     * 物品编码、物品序列号
     *
     * XXX - 必填/自然键？
     */
    @Column(length = 32)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * 物品条码
     */
    @Column(length = 30)
    public String getBarCode() {
        return barCode;
    }

    /**
     * 物品条码
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 物料附加的属性。
     *
     * （此属性不可索引）
     */
    @OneToMany(mappedBy = "material")
    @Cascade(CascadeType.ALL)
    public List<MaterialAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<MaterialAttribute> attributes) {
        if (attributes == null)
            throw new NullPointerException("attributes");
        this.attributes = attributes;
    }

    /**
     * 附件文件。
     */
    @CollectionOfElements
    @Cascade(CascadeType.ALL)
    @JoinTable(name = "MaterialAttachment", //
    /*        */inverseJoinColumns = @JoinColumn(name = "userFile"))
    public List<UserFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFile> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    @OneToMany(mappedBy = "material")
    @Cascade(CascadeType.ALL)
    public List<MaterialWarehouseOption> getOptions() {
        return options;
    }

    public void setOptions(List<MaterialWarehouseOption> options) {
        this.options = options;
    }

    @OneToMany(mappedBy = "material")
    @Cascade(CascadeType.ALL)
    public List<MaterialPreferredLocation> getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(List<MaterialPreferredLocation> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

}
