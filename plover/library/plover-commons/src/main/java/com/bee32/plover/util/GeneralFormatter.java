package com.bee32.plover.util;

import java.util.Set;

public class GeneralFormatter
        extends ObjectFormatter<Object> {

    public GeneralFormatter() {
        super();
    }

    public GeneralFormatter(PrettyPrintStream out, Set<Object> occurred) {
        super(out, occurred);
    }

    @Override
    protected void formatId(FormatStyle format, Object val) {
        if (!format.isIdentityIncluded())
            return;
        String typeName = getClass().getSimpleName();
        int id = System.identityHashCode(this);
        String idHex = Integer.toHexString(id);
        out.print(typeName);
        out.print("@");
        out.print(idHex);
    }

    public static String toString(Object obj, FormatStyle format, int depth) {
        GeneralFormatter formatter = new GeneralFormatter();
        formatter.format(obj, format, depth);
        return formatter.toString();
    }

}
