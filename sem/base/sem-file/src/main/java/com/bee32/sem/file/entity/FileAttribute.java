package com.bee32.sem.file.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;

/**
 * Updated when * fileBlob.mtime > fileattr[*].mtime.
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "file_attribute_seq", allocationSize = 1)
public class FileAttribute
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int KEY_LENGTH = 30;
    public static final int STR_VAL_LENGTH = 120;

    FileBlob blob;

    String key;
    int intVal;
    double floatVal;
    String strVal;

    @NaturalId
    @ManyToOne
    public FileBlob getBlob() {
        return blob;
    }

    public void setBlob(FileBlob blob) {
        this.blob = blob;
    }

    @NaturalId
    @Column(length = KEY_LENGTH, nullable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(nullable = false)
    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    @Column(nullable = false)
    public double getFloatVal() {
        return floatVal;
    }

    public void setFloatVal(double floatVal) {
        this.floatVal = floatVal;
    }

    @Column(length = STR_VAL_LENGTH)
    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(blob), key);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (blob == null)
            throw new NullPointerException("blob");
        if (key == null)
            throw new NullPointerException("key");
        return selectors(//
                selector(prefix + "blob", blob), //
                new Equals(prefix + "key", key));
    }

}
