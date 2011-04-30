package com.bee32.plover.orm.ext.meta;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.bee32.plover.orm.ext.typepref.TypePrefEntity;

@Entity
public class EntityInfo
        extends TypePrefEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Only used if TYPE = EntityOtf.class
     */
    String nameOtf;
    String alias;
    String description;

    List<EntityColumn> columns;

    @Column(length = 20)
    public String getNameOtf() {
        return nameOtf;
    }

    public void setNameOtf(String nameOtf) {
        this.nameOtf = nameOtf;
    }

    @Column(length = 50)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany
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
