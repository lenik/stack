package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.util.EntityDto;

public abstract class CreateOrEditFormHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    protected boolean _createOTF;

    @Override
    public EntityActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception {
        boolean create = req.methodEquals("create");

        EntityDto<E, K> dto = null;
        Integer dtoSelection = eh.getSelection(SelectionMode.CREATE_EDIT);

        E entity = null;
        K id = null;

        if (!create) {
            String _id = req.getParameter("id");
            id = eh.parseRequiredId(_id);

            entity = dataManager.get(eh.getEntityType(), id);

            if (entity == null) {
                if (!_createOTF)
                    return Javascripts.alertAndBack("编辑的对象不存在。" + eh.getHint(id)).dump(result);

                create = true;

            } else {
                dto = eh.newDto(dtoSelection);

                doLoad(entity, dto);
            }
        }

        if (create) {
            E newEntity = eh.newEntity();

            if (id != null)
                EntityAccessor.setId(newEntity, id);

            dto = eh.newDto(dtoSelection);

            // Entity-initializors, so as to populate the transient properties.
            doLoad(newEntity, dto);

            entity = newEntity;
        }

        String _VERB = create ? "CREATE" : "MODIFY";
        result.put("_create", create);
        result.put("_verb", result.V.get(_VERB));

        result.entity = entity;
        result.dto = dto;
        result.put("it", dto);
        return result;
    }

    protected abstract void doLoad(E entity, EntityDto<E, K> dto);

}
