package com.bee32.plover.orm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.arch.util.dto.BaseDto;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;

public abstract class EntityDto_VTU<E extends Entity<K>, K extends Serializable>
        extends BaseDto<E, IEntityMarshalContext>
        implements IMultiFormat {

    private static final long serialVersionUID = 1L;

    protected Class<? extends K> keyType;

    public EntityDto_VTU() {
        super();
    }

    public EntityDto_VTU(int selection) {
        super(selection);
    }

    @Override
    public void initSourceType(Class<? extends E> entityType) {
        super.initSourceType(entityType);
        keyType = EntityUtil.getKeyType(entityType);
    }

    protected final Class<? extends E> getEntityType() {
        return sourceType;
    }

    protected final Class<? extends K> getKeyType() {
        return keyType;
    }

    /**
     * <pre>
     * BASE LAYER: Constructor helpers
     * -----------------------------------------------------------------------
     *      * id-related helpers
     * -----------------------------------------------------------------------
     * </pre>
     */

    protected static <E extends IEntity<K>, K extends Serializable> K id(E entity) {
        if (entity == null)
            return null;
        else
            return entity.getId();
    }

    protected static <D extends EntityDto<?, K>, K extends Serializable> K id(D dto) {
        if (dto == null)
            return null;
        else
            return dto.getId();
    }

    protected static <D extends EntityDto<?, K>, K extends Serializable> List<K> id(Iterable<? extends D> dtos) {
        List<K> idList = new ArrayList<K>();

        if (dtos != null)
            for (D dto : dtos)
                idList.add(id(dto));

        return idList;
    }

    protected static <E extends IEntity<K>, K extends Serializable> List<K> idOfEntities(Iterable<? extends E> entities) {
        List<K> idList = new ArrayList<K>();

        if (entities != null)
            for (E entity : entities)
                idList.add(id(entity));

        return idList;
    }

    @Override
    public String toString() {
        return toString(FormatStyle.DEFAULT);
    }

    @Override
    public String toString(FormatStyle format) {
        PrettyPrintStream buf = new PrettyPrintStream();
        toString(buf, format);
        return buf.toString();
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        toString(out, format, null, 0);
    }

}
