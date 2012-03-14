package com.bee32.plover.ox1.c;

import java.io.Serializable;
import java.util.Map;

import javax.free.ParseException;

import org.hibernate.LazyInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

public abstract class CEntityDto<E extends CEntity<K>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(CEntityDto.class);

    public static final int OWNER_SKIP = 0x8000_0000;

    UserDto owner;
    Integer aclId;

    public CEntityDto() {
        super();
    }

    public CEntityDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        if (!selection.contains(OWNER_SKIP)) {
            User _owner = source.getOwner();
            try {
                owner = mref(UserDto.class, 0, _owner);
            } catch (LazyInitializationException e) {
                logger.error("Failed to marshal owner, maybe session expired? " + e.getMessage());
                owner = null;
                // MarshalType.SKIP;
            }
        }

        aclId = source.getAclId();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);

        if (!selection.contains(OWNER_SKIP))
            merge(target, "owner", owner);

        if (aclId != null)
            CEntityAccessor.setAclId(target, aclId);
    }

    @Override
    protected void __parse(TextMap map)
            throws ParseException {
        super.__parse(map);

        Integer aclId = map.getNInt("aclId");
        if (aclId != null)
            setAclId(aclId);

        Integer ownerId = map.getNInt("owner.id");
        if (ownerId != null)
            owner = new UserDto().ref(ownerId);
    }

    @Override
    protected void __export(Map<String, Object> map) {
        super.__export(map);

        if (aclId != null)
            map.put("aclId", aclId);

        // export owner.*
    }

    public Integer getAclId() {
        return aclId;
    }

    public void setAclId(Integer aclId) {
        this.aclId = aclId;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public String getOwnerDisplayName() {
        if (owner == null)
            return "";
        else
            return owner.getDisplayName();
    }

}
