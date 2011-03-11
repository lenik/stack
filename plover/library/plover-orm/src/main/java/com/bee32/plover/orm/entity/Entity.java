package com.bee32.plover.orm.entity;

import javax.free.Nullables;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.bee32.plover.arch.Component;
import com.bee32.plover.model.Model;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class Entity<K>
        extends Model
        implements IEntity<K>, IPopulatable, IFormatString {

    private static final long serialVersionUID = 1L;

    protected K id;

    private int version;

    public Entity() {
        super();
    }

    public Entity(String name) {
        super(name);
    }

    @Override
    public K getId() {
        return id;
    }

    void setId(K id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    protected boolean equalsSpecific(Component obj) {
        if (id == null)
            return false;

        @SuppressWarnings("unchecked")
        Entity<K> other = (Entity<K>) obj;

        if (other.id == null)
            return false;

        if (!id.equals(other.id))
            return false;

        return true;
    }

    @Override
    protected int hashCodeSpecific() {
        if (id != null)
            return id.hashCode();
        else
            return super.hashCodeSpecific();
    }

    @Override
    public void toString(PrettyPrintStream out, EntityFormat format) {
        switch (format) {
        case VERBOSE:
            String rts = ReflectionToStringBuilder.toString(this, //
                    ToStringStyle.SHORT_PREFIX_STYLE, false, false, Entity.class);

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
