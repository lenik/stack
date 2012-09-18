package com.bee32.sem.world.color;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 调色板
 *
 * 用于色彩信息交换的标准调色板。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "palette_seq", allocationSize = 1)
public class Palette
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    List<PaletteEntry> entries = new ArrayList<PaletteEntry>();

    public Palette() {
        super();
    }

    public Palette(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    public Palette(int order, String name, String label) {
        super(order, name, label);
    }

    public Palette(String name, String label, String description) {
        super(name, label, description);
    }

    public Palette(String name, String label) {
        super(name, label);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Palette)
            _populate((Palette) source);
        else
            super.populate(source);
    }

    protected void _populate(Palette o) {
        super._populate(o);
        entries = CopyUtils.copyList(o.entries);
    }

    /**
     * 条目列表
     *
     * 调色板中定义的所以色彩条目.
     */
    @OneToMany(mappedBy = "palette", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PaletteEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<PaletteEntry> entries) {
        if (entries == null)
            throw new NullPointerException("entries");
        this.entries = entries;
    }

    public PaletteEntry getEntry(String name) {
        if (name == null)
            throw new NullPointerException("name");

        for (PaletteEntry _color : entries) {
            String _name = _color.getName();
            if (name.equals(_name))
                return _color;
        }

        return null;
    }

    public void setEntry(String name, PaletteEntry entry) {
        if (name == null)
            throw new NullPointerException("name");
        if (entry == null)
            throw new NullPointerException("entry");

        // Replace existing color
        for (int i = 0; i < entries.size(); i++) {
            PaletteEntry _entry = entries.get(i);
            String _name = _entry.getName();

            if (_name.equals(name)) {
                entries.set(i, entry);
                return;
            }
        }

        // Or add new color.
        entries.add(entry);
    }

}
