package com.bee32.plover.orm.web.basic;

import java.io.Serializable;

import org.springframework.dao.DataIntegrityViolationException;

import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

public class DeleteHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    public DeleteHandler(Class<E> entityType) {
        super(entityType);
    }

    @Override
    protected ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws Exception {
        String idString = req.getParameter("id");
        K id;
        try {
            id = eh.parseRequiredId(idString);
        } catch (Exception e) {
            return Javascripts.alertAndBack("非法对象标识: " + idString + ": " + e.getMessage()).dump(result);
        }

        E entity = ctx.data.access(eh.getEntityType()).getOrFail(id);
        if (entity == null)
            return Javascripts.alertAndBack("对象不存在: " + idString).dump(result);

        EntityFlags ef = EntityAccessor.getFlags(entity);
        if (ef.isLocked())
            return Javascripts.alertAndBack("对象被锁定: " + idString).dump(result);
        // if (ef.isDeleted())
        // return Javascripts.alertAndBack("对象被标记为删除: " + idString).dump(result);

        try {
            ctx.data.access(eh.getEntityType()).delete(entity);
        } catch (DataIntegrityViolationException e) {
            return Javascripts.alertAndBack("不能删除正在使用中的对象。" + eh.getHint(id)).dump(result);
        }

        return result.sendRedirect("index.do");
    }

}
