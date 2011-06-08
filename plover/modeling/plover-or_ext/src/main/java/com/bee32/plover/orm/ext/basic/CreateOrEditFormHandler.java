package com.bee32.plover.orm.ext.basic;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.ext.util.EntityAction;
import com.bee32.plover.servlet.mvc.ResultView;

public class CreateOrEditFormHandler
        extends EntityHandler {

    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception {
        boolean create = view.isMethod("create");

        Dto dto = null;

        E entity = null;
        K id = null;

        if (!create) {
            String _id = req.getParameter("id");
            id = parseRequiredId(_id);

            entity = dataManager.get(getEntityType(), id);

            if (entity == null) {
                if (!_createOTF)
                    return Javascripts.alertAndBack("编辑的对象不存在。" + hint(id)).dump(req, resp);

                create = true;

            } else {
                dto = newDto(_dtoSelection);

                doAction(EntityAction.LOAD, entity, dto);
            }
        }

        if (create) {
            E newEntity = newEntity();

            if (id != null)
                EntityAccessor.setId(newEntity, id);

            dto = newDto(_dtoSelection);

            // Entity-initializors, so as to populate the transient properties.
            doAction(EntityAction.LOAD, newEntity, dto);

            entity = newEntity;
        }

        String _VERB = create ? "CREATE" : "MODIFY";
        view.put("_create", create);
        view.put("_verb", view.V.get(_VERB));

        view.entity = entity;
        view.dto = dto;
        view.put("it", dto);
        return view;
    }

}
