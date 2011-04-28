package com.bee32.plover.orm.entity;

import java.util.Set;

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

    static {
        addStopClass(EntityBase.class);
        addStopClass(Entity.class);
    }

}
