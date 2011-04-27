package com.bee32.plover.orm.builtin;

import javax.free.ParseException;

import com.bee32.plover.arch.util.ParameterMap;
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

    @Override
    protected void _parse(ParameterMap map)
            throws ParseException {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
