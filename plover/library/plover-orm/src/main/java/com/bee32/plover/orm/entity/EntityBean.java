package com.bee32.plover.orm.entity;

import javax.free.Nullables;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bee32.plover.arch.Component;
import com.bee32.plover.model.Model;
import com.bee32.plover.util.PrettyPrintStream;

/**
 * You may annotate the concrete entities with:
 * <ul>
 * <li>&#64;Entity
 * <li>&#64;Table
 * </ul>
 */
@MappedSuperclass
public abstract class EntityBean<K>
        extends Model
        implements IEntity<K>, IPopulatable, IFormatString {

    private static final long serialVersionUID = 1L;

    protected K id;

    int version;

    public EntityBean() {
        super();
    }

    public EntityBean(String name) {
        super(name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public K getId() {
        return id;
    }

    void setId(K id) {
        this.id = id;
    }

    @Version
    public int getVersion() {
        return version;
    }

    void setVersion(int version) {
        this.version = version;
    }

    @Override
    public void populate(Object source) {
    }

    @Override
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected final boolean equalsSpecific(Component obj) {
        @SuppressWarnings("unchecked")
        EntityBean<K> other = (EntityBean<K>) obj;

        if (version != other.version)
            return false;

        if (id != null) {
            if (other.id == null)
                return false;

            if (!id.equals(other.id))
                return false;

            return true;
        } else if (other.id != null)
            return false;

        return equalsEntity(other);
    }

    protected boolean equalsEntity(EntityBean<K> otherEntity) {
        return false;
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    @Override
    protected final int hashCodeSpecific() {
        int hash = 0xbabade33 * version;

        if (id != null)
            hash += id.hashCode();
        else
            hash += hashCodeEntity();

        return hash;
    }

    protected int hashCodeEntity() {
        return super.hashCodeSpecific();
    }

    @Override
    public void toString(PrettyPrintStream out, EntityFormat format) {
        switch (format) {
        case VERBOSE:
            String rts = ReflectionToStringBuilder.toString(this, //
                    ToStringStyle.SHORT_PREFIX_STYLE, false, false, EntityBean.class);

            out.print(rts);
            break;

        case SHORT:
        case LOG_ENTRY:
            out.print(name);
            String typeName = getClass().getSimpleName();

            if (!Nullables.equals(name, typeName)) {
                out.print("/");
                out.print(typeName);
            }

            if (id != null) {
                out.print(" [id=");
                out.print(id);
                out.print(", version=");
                out.print(version);
                out.print("]");
            }
            break;
        }
    }

    @Override
    public final String toString() {
        return toString(EntityFormat.DEFAULT);
    }

    public final String toString(EntityFormat format) {
        PrettyPrintStream out = new PrettyPrintStream();
        toString(out, format);
        return out.toString();
    }

}
