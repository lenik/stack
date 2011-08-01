package com.bee32.sem.file.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Blue;

/**
 * Updated when * fileBlob.mtime > fileattr[*].mtime.
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "file_attribute_seq", allocationSize = 1)
public class FileAttribute
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

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
    @Column(length = 30, nullable = false)
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

    @Column(length = 120)
    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        FileAttribute o = (FileAttribute) other;

        if (!blob.equals(o.blob))
            return false;

        if (!key.equals(o.key))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += blob.hashCode();
        hash += key.hashCode();
        return hash;
    }

}
