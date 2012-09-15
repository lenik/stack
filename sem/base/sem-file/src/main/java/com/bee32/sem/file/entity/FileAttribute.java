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
 * 文件属性
 *
 * 对上传共享的文件的描述
 *
 * Updated when * fileBlob.mtime > fileattr[*].mtime.
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "file_attribute_seq", allocationSize = 1)
public class FileAttribute
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 30;
    public static final int STR_VAL_LENGTH = 120;

    UserFile file;

    String name;
    int intVal;
    double floatVal;
    String strVal;

    @Override
    public void populate(Object source) {
        if (source instanceof FileAttribute)
            _populate((FileAttribute) source);
        else
            super.populate(source);
    }

    protected void _populate(FileAttribute o) {
        super._populate(o);
        file = o.file;
        name = o.name;
        intVal = o.intVal;
        floatVal = o.floatVal;
        strVal = o.strVal;
    }

    /**
     * 用户文件
     *
     * 本属性所对应的用户文件
     */
    @NaturalId
    @ManyToOne
    public UserFile getFile() {
        return file;
    }

    public void setFile(UserFile file) {
        this.file = file;
    }

    /**
     * 属性名称
     *
     * 本属性的名称
     */
    @NaturalId
    @Column(length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    /**
     * 整数属性
     *
     * 文件上的整数属性
     */
    @Column(nullable = false)
    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    /**
     * 小数属性
     *
     * 文件上的小数属性
     */
    @Column(nullable = false)
    public double getFloatVal() {
        return floatVal;
    }

    public void setFloatVal(double floatVal) {
        this.floatVal = floatVal;
    }

    /**
     * 字符串属性
     *
     * 文件上的字符串属性
     */
    @Column(length = STR_VAL_LENGTH)
    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(file), //
                name);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (file == null)
            throw new NullPointerException("file");
        if (name == null)
            throw new NullPointerException("key");
        return selectors(//
                selector(prefix + "file", file), //
                new Equals(prefix + "name", name));
    }

}
