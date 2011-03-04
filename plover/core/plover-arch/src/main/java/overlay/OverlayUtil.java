package overlay;

import javax.free.Strings;

public class OverlayUtil {

    public static final String overlayPackage = Overlay.class.getPackage().getName();

    /**
     * Get overlay class for specific extension.
     *
     * @return <code>null</code> If the overlay class for specific extension doesn't exist.
     */
    public static Class<?> getOverlay(Class<?> clazz, String extension) {
        String Extension = Strings.ucfirst(extension);
        String overlayClassName = overlayPackage + "." + clazz.getName() + Extension;
        Class<?> overlayClass;
        try {
            overlayClass = Class.forName(overlayClassName);
            return overlayClass;
        } catch (ClassNotFoundException e) {
        }

        Class<?> superclass = clazz.getSuperclass();
        if (superclass == null)
            return null;

        return getOverlay(superclass, extension);
    }

}
