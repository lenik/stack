package com.bee32.sem.world.color;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 调色板条目
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "palette_entry_seq", allocationSize = 1)
public class PaletteEntry
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 30;

    Palette palette;
    String name;
    final TrueColor trueColor;

    public PaletteEntry() {
        trueColor = new TrueColor();
    }

    public PaletteEntry(Palette palette, String name, TrueColor color) {
        if (palette == null)
            throw new NullPointerException("palette");
        if (name == null)
            throw new NullPointerException("name");
        if (color == null)
            throw new NullPointerException("color");
        this.palette = palette;
        this.name = name;
        this.trueColor = color;
    }

    /**
     * 调色板
     */
    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        if (palette == null)
            throw new NullPointerException("palette");
        this.palette = palette;
    }

    /**
     * 颜色代码/色号
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
     * 颜色定义
     */
    @Transient
    public TrueColor getTrueColor() {
        return trueColor;
    }

    @Column(name = "trueColor", nullable = false)
    long get_trueColor() {
        return trueColor.getLong();
    }

    void set_trueColor(long _trueColor) {
        trueColor.setLong(_trueColor);
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(palette), name);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return new And(//
                selector(prefix + "palette", palette), //
                new Equals(prefix + "name", name));
    }

}
