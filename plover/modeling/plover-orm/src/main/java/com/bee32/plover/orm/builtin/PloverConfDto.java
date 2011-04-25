package com.bee32.plover.orm.builtin;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class PloverConfDto
        extends EntityDto<PloverConf, String> {

    private static final long serialVersionUID = 1L;

    private String value;
    private String description;

    @Override
    protected void _marshal(PloverConf source) {
        value = source.getValue();
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, PloverConf target) {
        target.setValue(value);
        target.setDescription(description);
    }

}
