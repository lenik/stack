package com.bee32.plover.orm.builtin;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size = 100)
public class PloverConf
        extends com.bee32.plover.orm.entity.Entity<String> {

    private static final long serialVersionUID = 1L;

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
    @Override
    public String getId() {
        return id;
    }

    @Override
    protected void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static PloverConf VERSION = new PloverConf("version", "1.0");

}
