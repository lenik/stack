package com.bee32.plover.orm.builtin;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

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
    protected void _unmarshalTo(PloverConf target) {
        target.setValue(value);
        target.setDescription(description);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        value = map.getString("value");
        description = map.getString("description");
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
