package com.bee32.plover.arch.type;

import javax.free.Strings;

public class FriendTypes {

    public static Class<?> getFriendType(Class<?> type, String mode)
            throws ClassNotFoundException {
        String ucMode = Strings.ucfirst(mode);

        String fqcn = type.getName();
        ClassLoader loader = type.getClassLoader();

        String bySuffix = fqcn + ucMode;
        try {
            return Class.forName(bySuffix, false, loader);
        } catch (ClassNotFoundException e) {
            int dot = fqcn.lastIndexOf('.');
            String cn = fqcn.substring(dot + 1);
            String p = fqcn.substring(0, dot);
            String psibling = p + "." + mode;

            String byPSibling = psibling + "." + cn + ucMode;

            try {
                return Class.forName(byPSibling, false, loader);
            } catch (ClassNotFoundException e1) {
                dot = p.lastIndexOf('.');
                String pp;
                if (dot == -1)
                    pp = "";
                else
                    pp = p.substring(0, dot);

                String ppsibling = pp + "." + mode;
                String byPPSibling = ppsibling + "." + cn + ucMode;

                return Class.forName(byPPSibling, false, loader);
            }
        }
    }

    /**
     * Get any DTO type for the nearest ancestor of the given entity type.
     *
     * @return <code>null</code> if no DTO type found.
     */
    public static Class<?> getInheritedFriendType(Class<?> type, String mode) {
        if (type == null)
            throw new NullPointerException("type");

        try {
            return getFriendType(type, mode);
        } catch (ClassNotFoundException e) {
        }

        Class<?> parentType = type.getSuperclass();
        if (parentType == null)
            return null;

        return getInheritedFriendType(parentType, mode);
    }

}
