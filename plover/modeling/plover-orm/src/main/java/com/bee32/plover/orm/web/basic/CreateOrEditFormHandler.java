package com.bee32.plover.orm.web.basic;

import java.io.Serializable;

import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.orm.web.IEntityForming;
import com.bee32.plover.orm.web.SelectionMode;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

public class CreateOrEditFormHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    protected boolean _createOTF;

    IEntityForming<E, K> forming;

    public CreateOrEditFormHandler(Class<E> entityType, IEntityForming<E, K> forming) {
        super(entityType);

        if (forming == null)
            throw new NullPointerException("forming");

        this.forming = forming;
    }

    @Override
    protected ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws Exception {
        String actionName = req.getActionName();
        boolean create = actionName.startsWith("create");

        if (create) {
            result.put("method", "create");
            result.put("METHOD", result.V.get("create"));
        } else {
            result.put("method", "edit");
            result.put("METHOD", result.V.get("edit"));
        }
        result.setViewName("form");

        EntityDto<E, K> dto = null;
        Integer dtoSelection = eh.getSelection(SelectionMode.CREATE_EDIT);

        E entity = null;
        K id = null;

        if (!create) {
            String _id = req.getParameter("id");
            id = eh.parseRequiredId(_id);

            entity = ctx.data.access(eh.getEntityType()).get(id);

            if (entity == null) {
                if (!_createOTF)
                    return Javascripts.alertAndBack("编辑的对象不存在。" + eh.getHint(id)).dump(result);

                create = true;

            } else {
                dto = eh.newDto(dtoSelection);

                forming.loadForm(entity, dto);
            }
        }

        if (create) {
            E newEntity = eh.newEntity();

            if (id != null)
                EntityAccessor.setId(newEntity, id);

            dto = eh.newDto(dtoSelection);

            // Entity-initializors, so as to populate the transient properties.
            forming.createForm(newEntity, dto);

            entity = newEntity;
        }

        String _VERB = create ? "CREATE" : "MODIFY";
        result.put("_create", create);
        result.put("_verb", result.V.get(_VERB));

        result.put("entity", entity);
        result.put("it", dto);
        return result;
    }

}
