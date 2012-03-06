package com.bee32.plover.ox1.meta;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.typePref.TypePrefEntity;

@Entity
public class EntityInfo
        extends TypePrefEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Only used if TYPE = EntityOtf.class
     */
    String nameOtf;
    String label;
    String description;

    List<EntityColumn> columns;

X-Population

    @Column(length = 20)
    public String getNameOtf() {
        return nameOtf;
    }

    public void setNameOtf(String nameOtf) {
        this.nameOtf = nameOtf;
    }

    @Column(length = 50)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<EntityColumn> getColumns() {
        if (columns == null) {
            synchronized (this) {
                if (columns == null) {
                    columns = new ArrayList<EntityColumn>();
                }
            }
        }
        return columns;
    }

    public void setColumns(List<EntityColumn> columns) {
        this.columns = columns;
    }

}
