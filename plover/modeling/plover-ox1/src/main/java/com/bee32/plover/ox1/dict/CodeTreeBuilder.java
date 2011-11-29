package com.bee32.plover.ox1.dict;

import javax.free.StartswithPreorder;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.util.FormatStyle;

public class CodeTreeBuilder
        extends PoTreeBuilder<Entity<String>, String> {

    public CodeTreeBuilder() {
        super(DictCodeMapper.INSTANCE, StartswithPreorder.getInstance());
    }

    @Override
    protected String formatEntry(PoNode node) {
        String key = (String) node.getKey();
        if (node.isVirtual())
            return key + " (virtual)";
        Entity<String> entity = (Entity<String>) node.getData();
        return key + ": " + entity.toString(FormatStyle.SIMPLE);
    }

}
