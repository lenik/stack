package com.bee32.plover.orm.builtin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.BatchSize;

import com.bee32.plover.orm.entity.EntitySpec;

@Entity
@BatchSize(size = 100)
public class PloverConf
        extends EntitySpec<String> {

    private static final long serialVersionUID = 1L;

    public static final int ID_LENGTH = 40;
    public static final int VALUE_LENGTH = 200;
    public static final int DESCRIPTION_LENGTH = 200;

    private String id;
    private String value;
    private String description;

    public PloverConf() {
    }

    public PloverConf(String key, String value) {
        this.id = key;
        this.value = value;
    }

    @Id
    @Column(length = ID_LENGTH)
    @Override
    public String getId() {
        return id;
    }

    @Override
    protected void setId(String id) {
        this.id = id;
    }

    @Column(length = VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(length = DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static PloverConf VERSION = new PloverConf("version", "1.0");

}
