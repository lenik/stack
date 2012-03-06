package com.bee32.sem.world.color;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

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
    final TrueColor trueColor;

    public PaletteEntry() {
        trueColor = new TrueColor();
    }

    public PaletteEntry(Palette palette, String label, TrueColor color) {
        if (palette == null)
            throw new NullPointerException("palette");
        if (label == null)
            throw new NullPointerException("label");
        if (color == null)
            throw new NullPointerException("color");
        this.palette = palette;
        this.label = label;
        this.trueColor = color;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof PaletteEntry)
            _populate((PaletteEntry) source);
        else
            super.populate(source);
    }

    protected void _populate(PaletteEntry o) {
        super._populate(o);
        palette = o.palette;
        trueColor.populate(o.trueColor);
    }

    /**
     * 调色板
     */
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

}
