package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
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

    List<MaterialAttribute> attributes = new ArrayList<MaterialAttribute>();
    List<UserFile> attachments = new ArrayList<UserFile>();
    Set<StockLocation> preferredLocations = new HashSet<StockLocation>();

    // ------------------------------------------------------------------------
    // 需要索引的常用的物料属性（这些属性和单位还算无关）。
    //
    // String packageSize;
    // String packageWeight;
    // ------------------------------------------------------------------------

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
     * 物料附加的属性。
     *
     * （此属性不可索引）
     */
    @OneToMany(mappedBy = "material")
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
    public List<UserFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFile> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    @CollectionOfElements
    @Cascade(CascadeType.ALL)
    public Set<StockLocation> getPreferredLocations() {
        return preferredLocations;
    }

    public void setPreferredLocations(Set<StockLocation> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

}
