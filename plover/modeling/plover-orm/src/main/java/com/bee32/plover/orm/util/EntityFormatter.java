package com.bee32.plover.orm.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.ObjectFormatter;
import com.bee32.plover.util.PrettyPrintStream;

public class EntityFormatter
        extends ObjectFormatter<Entity<?>> {

    public EntityFormatter() {
        super();
    }

    public EntityFormatter(PrettyPrintStream out, Set<Object> occurred) {
        super(out, occurred);
    }

    @Override
    protected void formatId(FormatStyle format, Entity<?> val) {
        String name = val.getName();

        // "NAME@TYPE:ID.VER"
        if (format.isIdentityIncluded()) {
            String typeName = val.getClass().getSimpleName();
            if (!typeName.equals(name))
                out.print(name);

            out.print("@");
            out.print(typeName);

            Object id = val.getId();
            if (id != null) {
                out.print(':');
                out.print(id);

                int version = val.getVersion();
                if (version != 0) {
                    out.print('.');
                    out.print(version);
                }
            }
        }
    }

    @Override
    protected void formatCollection(Collection<?> val, FormatStyle format, int depth) {
        if (!Hibernate.isInitialized(val)) {
            out.print("(n/a: not initialized)");
            return;
        }
        super.formatCollection(val, format, depth);
    }

    @Override
    protected void formatMap(Map<?, ?> val, FormatStyle format, int depth) {
        if (!Hibernate.isInitialized(val)) {
            out.print("(n/a: not initialized)");
            return;
        }
        super.formatMap(val, format, depth);
    }

    static {
        addStopClass(EntityBase.class);
        addStopClass(Entity.class);
    }

}
