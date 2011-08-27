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

    List<PaletteColor> colors = new ArrayList<PaletteColor>();

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
    public List<PaletteColor> getColors() {
        return colors;
    }

    public void setColors(List<PaletteColor> colors) {
        if (colors == null)
            throw new NullPointerException("colors");
        this.colors = colors;
    }

    public PaletteColor getColor(String name) {
        if (name == null)
            throw new NullPointerException("name");

        for (PaletteColor _color : colors) {
            String _name = _color.getName();
            if (name.equals(_name))
                return _color;
        }

        return null;
    }

    public void setColor(String name, PaletteColor color) {
        if (name == null)
            throw new NullPointerException("name");
        if (color == null)
            throw new NullPointerException("color");

        // Replace existing color
        for (int i = 0; i < colors.size(); i++) {
            PaletteColor _color = colors.get(i);
            String _name = _color.getName();

            if (_name.equals(name)) {
                colors.set(i, color);
                return;
            }
        }

        // Or add new color.
        colors.add(color);
    }

}
