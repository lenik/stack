package com.bee32.zebra.tk.site;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.db.meta.TableName;
import net.bodz.bas.repr.path.PathToken;

public class CoTypes {

    public static String getPathToken(Class<?> clazz) {
        List<String> paths = new ArrayList<String>();
        return getPathToken(clazz, paths);
    }

    public static String getPathToken(Class<?> clazz, List<String> paths) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (paths == null)
            throw new NullPointerException("paths");

        paths.add(Strings.hyphenatize(clazz.getSimpleName()));

        TableName aTableName = clazz.getAnnotation(TableName.class);
        if (aTableName != null)
            paths.add(aTableName.value());

        PathToken aPathToken = clazz.getAnnotation(PathToken.class);
        if (aPathToken != null)
            paths.add(StringArray.join("/", aPathToken.value()));

        return paths.isEmpty() ? null : paths.get(paths.size() - 1);
    }

}
