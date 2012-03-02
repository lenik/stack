package com.bee32.plover.orm.builtin;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.util.TextUtil;

public class PloverConfDto
        extends EntityDto<PloverConf, Long> {

    private static final long serialVersionUID = 1L;

    String section;
    String key;
    String value;
    String description;

    @Override
    protected void _marshal(PloverConf source) {
        section = source.getSection();
        key = source.getKey();
        value = source.getValue();
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(PloverConf target) {
        target.setSection(section);
        target.setKey(key);
        target.setValue(value);
        target.setDescription(description);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        section = map.getString("section");
        key = map.getString("key");
        value = map.getString("value");
        description = map.getString("description");
    }

    @NLength(min = 1, max = PloverConf.SECTION_LENGTH)
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        if (section == null)
            throw new NullPointerException("section");
        this.section = TextUtil.normalizeSpace(section);
    }

    @NLength(min = 1, max = PloverConf.KEY_LENGTH)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (key == null)
            throw new NullPointerException("key");
        this.key = TextUtil.normalizeSpace(key);
    }

    @NLength(max = PloverConf.VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = TextUtil.normalizeSpace(value);
    }

    @NLength(max = PloverConf.DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = TextUtil.normalizeSpace(description);
    }

}
