package com.bee32.icsf.principal;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.PloverOx1Unit;

public class AbbrRefactor
        implements ITypeAbbrAware {

    public static void refactor(String src, String dst) {
        String _src = ABBR.abbr(src);
        String _dst = ABBR.abbr(dst);
        System.out.printf("repl -d -r '%s' '%s' .\n", _src, _dst);
    }

    public static void main(String[] args) {
        for (Class<?> c : new PloverOx1Unit().getLocalClasses()) {
            String fqcnNew = c.getName();
            String fqcnOld = fqcnNew.replace("com.bee32.plover.ox1", "com.bee32.icsf");
            refactor(fqcnOld, fqcnNew);
        }
    }

}
