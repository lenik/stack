package user.hibernate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnits;

public class ColorSystem {

    static PersistenceUnit unit = PersistenceUnits.getInstance();

    static {
        unit.addPersistedClass(Color.class);
        unit.addPersistedClass(RGB.class);
        unit.addPersistedClass(CMYK.class);
    }

    static CMYK white = new CMYK("White", 0, 0, 0, 0);
    static CMYK black = new CMYK("Black", 255, 255, 255, 255);

    public static Collection<Color> getPredefinedColors() {
        List<Color> colors = new ArrayList<Color>();

        try {
            for (Field field : java.awt.Color.class.getFields()) {
                if (!Modifier.isStatic(field.getModifiers()))
                    continue;

                java.awt.Color color = (java.awt.Color) field.get(null);

                RGB rgb = new RGB();

                rgb.setName(field.getName());

                rgb.setRed(color.getRed());
                rgb.setGreen(color.getGreen());
                rgb.setBlue(color.getBlue());

                colors.add(rgb);
            }
        } catch (Exception e) {
        }

        colors.add(white);
        colors.add(black);

        return colors;
    }

}
