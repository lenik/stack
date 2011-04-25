package com.bee32.plover.orm.ext.typepref;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.EntityExp;
import com.bee32.plover.orm.util.ITypeAbbrAware;

@MappedSuperclass
public class TypePrefEntity
        extends EntityExp<String>
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    private boolean earlyResolve;

    private String typeId;
    private Class<?> type;

    @Transient
    public boolean isEarlyResolve() {
        return earlyResolve;
    }

    public void setEarlyResolve(boolean earlyResolve) {
        this.earlyResolve = earlyResolve;
    }

    @Transient
    @Override
    public String getId() {
        return getTypeId();
    }

    @Override
    protected void setId(String id) {
        setTypeId(id);
    }

    @Id
    @Column(length = ABBR_LEN, nullable = false)
    protected String getTypeId() {
        return typeId;
    }

    protected void setTypeId(String typeId) {
        this.typeId = typeId;

        if (earlyResolve)
            type = resolveType(typeId);
        else
            type = null;
    }

    @Transient
    public Class<?> getType() {
        if (type == null && !earlyResolve)
            type = resolveType(typeId);
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
        this.typeId = ABBR.abbr(type);
    }

    protected Class<?> resolveType(String typeId) {
        Class<?> type;

        if (typeId == null)
            type = null;
        else
            try {
                type = ABBR.expand(typeId);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Bad type id: " + typeId, e);
            }

        return checkType(type);
    }

    protected Class<?> checkType(Class<?> type) {
        return type;
    }

    @Transient
    public String getDisplayName() {
        Class<?> type = getType();
        if (type == null)
            return null;

        return ClassUtil.getDisplayName(type);
    }

}
