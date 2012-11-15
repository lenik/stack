package net.sf.jasperreports.engine.fill;

import java.awt.GraphicsEnvironment;

public class FontList {

    public static void main(String[] args) {
        String[] availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
        for (String name : availableFontFamilyNames)
            System.out.println(name);
    }

}
