package com.bee32.sem.world.color;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.ICloneable;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.orm.entity.EmbeddablePiece;

/**
 * 自然色
 *
 * 调色色彩或一般RGB色彩。
 *
 * <p lang="en">
 * Natural Color
 */
@Embeddable
public class NaturalColor
        extends EmbeddablePiece
        implements ICloneable {

    private static final long serialVersionUID = 1L;

    Palette palette;
    String entryName;
    /* final */TrueColor trueColor = new TrueColor();

    @Override
    public Object clone() {
        NaturalColor copy = new NaturalColor();
        copy.palette = palette;
        copy.entryName = entryName;
        copy.trueColor = CopyUtils.clone(trueColor);
        return copy;
    }

    /**
     * 调色板
     *
     * 色彩对应的调色板。
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

    /**
     * 色彩名称
     *
     * 在色彩对应的调色板中定义的，本色彩条目的标准名称。
     */
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

    /**
     * 色彩值
     *
     * 色彩的 RGB 组合数值。
     */
    @Column(name = "trueColor", nullable = false)
    long get_trueColor() {
        return trueColor.getLong();
    }

    void set_trueColor(long _trueColor) {
        trueColor.setLong(_trueColor);
    }

    /**
     * 名称
     *
     * <p lang="en">
     * Name
     */
    @Transient
    public String getName() {
        if (palette != null)
            return palette.getName() + ":" + entryName;
        else
            return trueColor.toString();
    }

}
