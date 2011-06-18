package com.bee32.plover.orm.util;

import java.util.Set;

import com.bee32.plover.arch.util.dto.BaseDto;
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
    public synchronized void format(EntityDto<?, ?> dto, FormatStyle format, int depth) {
        String stereo;

        switch (dto.getMarshalType()) {
        case ID_REF:
            stereo = "ID:" + dto.getId();
            break;

        case ID_VER_REF:
            stereo = "ID-VER:" + dto.getId() + "." + dto.getVersion();
            break;

        case NAME_REF:
            stereo = "NAME:?";
            break;

        case OTHER_REF:
            stereo = "REF(*)";
            break;

        case SELECTION:
        default:
            stereo = null;
        }

        if (dto.isNullRef())
            stereo = "null";

        if (stereo != null) {
            out.print("《" + stereo + "》");
        } else {
            super.format(dto, format, depth);
        }
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

                Integer version = val.getVersion();
                if (version != null) {
                    out.print('.');
                    out.print(version);
                }
            }
        }
    }

    static {
        addStopClass(BaseDto.class);
        addStopClass(EntityDto.class);
    }

}
