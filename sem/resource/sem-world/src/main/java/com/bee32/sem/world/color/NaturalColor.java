package com.bee32.sem.world.color;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
public class NaturalColor
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Palette palette;
    String entryName;
    final TrueColor trueColor = new TrueColor();

    /**
     * 调色板。
     *
     * 当使用调色板时，trueColor 为色样的冗余，应该和 palette.getEntry(entryName).trueColor 相同。
     *
     * 当不使用调色板时（palette == null)，则忽略色样，仅读取 trueColor。
     */
    @ManyToOne
    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    @Column(length = PaletteEntry.NAME_LENGTH)
    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    @Transient
    public TrueColor getTrueColor() {
        if (palette == null)
            return trueColor;

        if (entryName == null)
            throw new IllegalStateException("Palette sample has not been set.");

        PaletteEntry _color = palette.getEntry(entryName);
        return _color.getTrueColor();
    }

    @Column(name = "trueColor", nullable = false)
    long get_trueColor() {
        return trueColor.getLong();
    }

    void set_trueColor(long _trueColor) {
        trueColor.setLong(_trueColor);
    }

    @Transient
    public String getName() {
        if (palette != null)
            return palette.getName() + ":" + entryName;
        else
            return trueColor.toString();
    }

}
