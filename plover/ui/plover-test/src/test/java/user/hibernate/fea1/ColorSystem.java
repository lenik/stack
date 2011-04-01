package user.hibernate.fea1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.free.UnexpectedException;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class ColorSystem
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Color.class);
        add(RGB.class);
        add(ARGB.class);
        add(CMYK.class);
    }

    static CMYK white = new CMYK("White", 0, 0, 0, 0);
    static CMYK black = new CMYK("Black", 255, 255, 255, 255);

    static ARGB gray = new ARGB("Gray 50%", 128, 128, 128, 50);

    public static Collection<Color> getPredefinedColors() {
        List<Color> colors = new ArrayList<Color>();

        for (Field field : java.awt.Color.class.getFields()) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;

            Class<?> fieldType = field.getType();
            if (!java.awt.Color.class.isAssignableFrom(fieldType))
                continue;

            String colorName = field.getName();
            java.awt.Color color;
            try {
                color = (java.awt.Color) field.get(null);
            } catch (Exception e) {
                throw new UnexpectedException(e);
            }

            RGB rgb = new RGB(colorName);

            rgb.setRed(color.getRed());
            rgb.setGreen(color.getGreen());
            rgb.setBlue(color.getBlue());

            colors.add(rgb);
        }

        colors.add(white);
        colors.add(black);
        colors.add(gray);

        return colors;
    }

}
