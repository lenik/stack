package com.bee32.plover.orm.builtin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.util.TextUtil;

@Entity
@BatchSize(size = 100)
@SequenceGenerator(name = "idgen", sequenceName = "plover_conf_seq", allocationSize = 1)
public class PloverConf
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int SECTION_LENGTH = 50;
    public static final int KEY_LENGTH = 50;
    public static final int VALUE_LENGTH = 200;
    public static final int DESCRIPTION_LENGTH = 200;

    private String section;
    private String key;
    private String value;
    private String description;

    public PloverConf() {
    }

    public PloverConf(String section, String key, String value) {
        this(section, key, value, null);
    }

    public PloverConf(String section, String key, String value, String description) {
        setSection(section);
        setKey(key);
        setValue(value);
        setDescription(description);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof PloverConf)
            _populate((PloverConf) source);
        else
            super.populate(source);
    }

    protected void _populate(PloverConf o) {
        super._populate(o);
        section = o.section;
        key = o.key;
        value = o.value;
        description = o.description;
    }

    @NaturalId
    @Column(length = SECTION_LENGTH, nullable = false)
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        if (section == null)
            throw new NullPointerException("section");
        this.section = TextUtil.normalizeSpace(section);
    }

    @NaturalId
    @Column(length = KEY_LENGTH, nullable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (key == null)
            throw new NullPointerException("key");
        this.key = TextUtil.normalizeSpace(key);
    }

    @Column(length = VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = TextUtil.normalizeSpace(value);
    }

    @Column(length = DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected Serializable naturalId() {
        if (section == null)
            throw new NullPointerException("section");
        if (key == null)
            throw new NullPointerException("key");
        return new IdComposite(section, key);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return And.of(//
                new Equals(prefix + "section", section), //
                new Equals(prefix + "key", key));
    }

}
