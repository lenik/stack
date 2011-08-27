package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.Map;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.c.CEntity;
import com.bee32.plover.orm.ext.c.CEntityAccessor;

public abstract class CEntityDto<E extends CEntity<K>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    Integer aclId;
    Integer ownerId;

    public CEntityDto() {
        super();
    }

    public CEntityDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        aclId = source.getAclId();
        ownerId = source.getOwnerId();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);

        if (aclId != null)
            CEntityAccessor.setAclId(target, aclId);

        if (ownerId != null)
            CEntityAccessor.setOwnerId(target, ownerId);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException {
        super.__parse(map);

        Integer aclId = map.getNInt("aclId");
        if (aclId != null)
            setAclId(aclId);

        Integer ownerId = map.getNInt("ownerId");
        if (ownerId != null)
            setOwnerId(ownerId);
    }

    @Override
    protected void __export(Map<String, Object> map) {
        super.__export(map);

        if (aclId != null)
            map.put("aclId", aclId);

        if (ownerId != null)
            map.put("ownerId", ownerId);
    }

    public Integer getAclId() {
        return aclId;
    }

    public void setAclId(Integer aclId) {
        this.aclId = aclId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

}
