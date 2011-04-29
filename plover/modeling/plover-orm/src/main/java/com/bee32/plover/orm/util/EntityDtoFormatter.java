package com.bee32.plover.orm.util;

import java.util.Set;

import com.bee32.plover.arch.util.DataTransferObject;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.ObjectFormatter;
import com.bee32.plover.util.PrettyPrintStream;

public class EntityDtoFormatter
        extends ObjectFormatter<EntityDto<?, ?>> {

    public EntityDtoFormatter() {
        super();
    }

    public EntityDtoFormatter(PrettyPrintStream out, Set<Object> occurred) {
        super(out, occurred);
    }

    @Override
    protected void formatId(FormatStyle format, EntityDto<?, ?> val) {
        String name = "《DTO》"; // val.getName();

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
        addStopClass(DataTransferObject.class);
        addStopClass(EntityDto.class);
    }

}
