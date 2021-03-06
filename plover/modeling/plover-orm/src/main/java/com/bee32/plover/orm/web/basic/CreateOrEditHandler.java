package com.bee32.plover.orm.web.basic;

import java.io.Serializable;

import javax.servlet.ServletException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.orm.web.IEntityForming;
import com.bee32.plover.orm.web.IPostUpdating;
import com.bee32.plover.orm.web.SelectionMode;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

public class CreateOrEditHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    protected boolean _createOTF;

    IEntityForming<E, K> forming;
    IPostUpdating<E, K> postUpdating;

    public CreateOrEditHandler(Class<E> entityType, IEntityForming<E, K> forming) {
        super(entityType);

        if (forming == null)
            throw new NullPointerException("forming");

        this.forming = forming;
    }

    public IPostUpdating<E, K> getPostUpdating() {
        return postUpdating;
    }

    /**
     * @param postUpdating
     *            May be null.
     */
    public void setPostUpdating(IPostUpdating<E, K> postUpdating) {
        this.postUpdating = postUpdating;
    }

    /**
     * Use <code>req.getAttribute("create"): Boolean</code> to distinguish create and update.
     */
    @Override
    protected ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws Exception {
        String actionName = req.getActionName();
        boolean create = actionName.startsWith("create");

        if (create) {
            result.put("method", "create");
            result.put("METHOD", result.V.get("create"));

            // return result.sendRedirect("index.do");
        } else {
            result.setViewName("index");
            result.put("METHOD", result.V.get("edit"));
        }

        Class<? extends E> entityType = eh.getEntityType();
        Integer dtoSelection = eh.getSelection(SelectionMode.CREATE_EDIT);
        EntityDto<E, K> dto = eh.newDto(dtoSelection).create();

        dto.parse(req);

        E entity = null;
        K id = dto.getId();

        if (!create) {
            if (id == null)
                throw new ServletException("id isn't specified");

            entity = ctx.data.access(entityType).get(id);
            if (entity == null) {
                if (!_createOTF)
                    return Javascripts.alertAndBack("????????????????????????????????????" + eh.getHint(id) + "\n\n" //
                            + "?????????????????????????????????????????????????????????????????????????????????\n" //
                            + "??????????????????????????????" //
                            + ClassUtil.getParameterizedTypeName(entity) + " [" + id + "]" //
                    ).dump(result);

                create = true;
            }

            else {
                Integer requestVersion = dto.getVersion();
                if (requestVersion != null && requestVersion != entity.getVersion())
                    return Javascripts.alertAndBack("?????????????????????" + eh.getHint(id) + "\n\n" //
                            + "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\n" //
                            + "??????????????????????????????" //
                    ).dump(result);
            }
        }

        if (create) {
            entity = eh.newEntity();

            if (id != null)
                EntityAccessor.setId(entity, id);
        }

        forming.saveForm(entity, dto);

        ctx.data.access(entityType).saveOrUpdate(entity);

        if (postUpdating != null)
            postUpdating.postUpdate(entity);

        // XXX Not used:
        // result.entity = entity;
        // result.dto = dto;

        // TODO normalizeURL??
        return result.sendRedirect("index.do");
    }

}
