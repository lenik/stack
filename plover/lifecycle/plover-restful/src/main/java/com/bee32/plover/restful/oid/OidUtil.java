package com.bee32.plover.restful.oid;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.free.ParseException;

public class OidUtil {

    /**
     * Get OID from the class type.
     *
     * @param clazz
     *            The referred class.
     * @return Internal integer array for the OID, if there is any.
     */
    public static OidVector getOid(Class<?> clazz) {
        return getOid(clazz.getName(), clazz);
    }

    public static <T extends Member & AnnotatedElement> OidVector getOid(T obj) {
        return getOid(obj.getName(), obj);
    }

    static boolean explicitOnly = true;

    /**
     * Get OID from an annoated element.
     *
     * If there's no @Oid found on the the annoated element, <code>null</code> is returned.
     *
     * @param name
     *            The alternative name to use when there's no @Oid annotation.
     * @param element
     *            The referred element.
     * @return Internal integer array for the OID, if there is any.
     */
    public static OidVector getOid(String name, AnnotatedElement element) {
        Oid oidAnnotation = element.getAnnotation(Oid.class);
        if (oidAnnotation != null) {
            int[] vector = oidAnnotation.value();
            return new OidVector(vector);
        }

        if (explicitOnly)
            return null;

        StringTokenizer st = new StringTokenizer(name, ".");
        List<Integer> ints = new ArrayList<Integer>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            ints.add(token.hashCode());
        }

        int[] vector = new int[ints.size()];
        for (int i = 0; i < ints.size(); i++)
            vector[i] = ints.get(i);

        return new OidVector(vector);
    }

    /**
     * Parse the OID string to internal integer array.
     *
     * @param delim
     *            The delimitor to use.
     * @param oidString
     *            The OID string, non-<code>null</code>.
     * @return The result array parsed.
     * @throws ParseException
     *             If the oidString isn't composed by integers and without any space.
     */
    public static OidVector parse(char delim, String oidString)
            throws ParseException {
        List<Integer> list = new ArrayList<Integer>(oidString.length() / 2);
        StringTokenizer st = new StringTokenizer(oidString, String.valueOf(delim));
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            try {
                int num = Integer.parseInt(token);
                list.add(num);
            } catch (NumberFormatException e) {
                throw new ParseException(e);
            }
        }

        int[] vector = new int[list.size()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = list.get(i);
        }
        return new OidVector(vector);
    }

    public static String shiftInteger(char delim, String string) {
        int pos = string.indexOf(delim);
        String head;
        if (pos == -1)
            head = string;
        else
            head = string.substring(0, pos);
        if (isNumber(head))
            return head;
        return null;
    }

    public static boolean isNumber(String str) {
        int i = str.length();
        while (--i >= 0) {
            char c = str.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }

}
