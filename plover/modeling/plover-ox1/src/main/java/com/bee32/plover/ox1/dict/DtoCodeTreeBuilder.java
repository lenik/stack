package com.bee32.plover.ox1.dict;

import javax.free.StartswithPreorder;

import com.bee32.plover.util.FormatStyle;

public class DtoCodeTreeBuilder
        extends PoTreeBuilder<NameDictDto<?>, String> {

    public DtoCodeTreeBuilder() {
        super(DtoCodeMapper.INSTANCE, StartswithPreorder.getInstance());
    }

    @Override
    protected String formatEntry(PoNode node) {
        String key = (String) node.getKey();
        if (node.isVirtual())
            return key + " (virtual)";
        NameDictDto<?> dto = (NameDictDto<?>) node.getData();
        return key + ": " + dto.toString(FormatStyle.SIMPLE);
    }

}
