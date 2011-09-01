package com.bee32.sem.world.color;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.dict.ShortNameDict;

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

    @OneToMany(mappedBy = "palette")
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
