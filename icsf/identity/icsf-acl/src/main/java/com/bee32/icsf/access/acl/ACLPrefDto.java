package com.bee32.icsf.access.acl;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.typePref.TypePrefDto;
import com.bee32.plover.util.TextUtil;

public class ACLPrefDto
        extends TypePrefDto<ACLPref> {

    private static final long serialVersionUID = 1L;

    ACLDto preferredACL;
    String description;

    @Override
    protected void _marshal(ACLPref source) {
        preferredACL = mref(ACLDto.class, source.getPreferredACL());
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(ACLPref target) {
        merge(target, "preferredACL", preferredACL);
        target.setDescription(description);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public ACLDto getPreferredACL() {
        return preferredACL;
    }

    public void setPreferredACL(ACLDto preferredACL) {
        if (preferredACL == null)
            preferredACL = new ACLDto().ref();
        this.preferredACL = preferredACL;
    }

    @NLength(max = ACLPref.DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = TextUtil.normalizeSpace(description);
    }

}
