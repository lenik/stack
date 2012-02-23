package com.bee32.plover.orm.access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bee32.plover.servlet.util.ServiceTemplateRC;

public abstract class AbstractEntityProcessor
        extends ServiceTemplateRC
        implements IEntityProcessor {

    @Override
    public int getPriority() {
        return 0;
    }

    @SafeVarargs
    protected static <T> List<T> listOf(T... selection) {
        return Arrays.asList(selection);
    }

    @SafeVarargs
    protected static <T> List<T> listOfNonNulls(T... selection) {
        List<T> list = new ArrayList<T>(selection.length);
        for (T item : selection)
            if (item != null)
                list.add(item);
        return Collections.unmodifiableList(list);
    }

}
