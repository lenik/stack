package com.bee32.plover.ox1.typePref;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.EntitySpec;
import com.bee32.plover.orm.util.ITypeAbbrAware;

/**
 * 按类型设置的配置项
 */
@MappedSuperclass
public abstract class TypePrefEntity
        extends EntitySpec<String>
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    private boolean earlyResolve;

    private Class<?> type;
    private String typeId;

    @Override
    public void populate(Object source) {
        if (source instanceof TypePrefEntity)
            _populate((TypePrefEntity) source);
        else
            super.populate(source);
    }

    protected void _populate(TypePrefEntity o) {
        super._populate(o);
        earlyResolve = o.earlyResolve;
        type = o.type;
        typeId = o.typeId;
    }

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

    /**
     * Get the type which as the primary key.
     *
     * @return Non-<code>null</code> expanded type.
     */
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

    /**
     * 类标识
     *
     * 类型的摘要标识（固定的10个字符长）。
     */
    @Id
    @Column(length = ABBR_LEN, nullable = false)
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;

        if (earlyResolve)
            type = resolveType(typeId);
        else
            type = null;
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

        return ClassUtil.getTypeName(type);
    }

}
