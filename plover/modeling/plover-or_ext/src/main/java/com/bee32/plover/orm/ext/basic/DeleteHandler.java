package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import org.springframework.dao.DataIntegrityViolationException;

import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;

public class DeleteHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    @Override
    public EntityActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception {
        String idString = req.getParameter("id");
        K id;
        try {
            id = eh.parseRequiredId(idString);
        } catch (Exception e) {
            return Javascripts.alertAndBack("非法对象标识: " + idString + ": " + e.getMessage()).dump(result);
        }

        Object entity = dataManager.fetch(eh.getEntityType(), id);
        if (entity != null)
            try {
                dataManager.delete(entity);
            } catch (DataIntegrityViolationException e) {
                return Javascripts.alertAndBack("不能删除正在使用中的对象。" + eh.getHint(id)).dump(result);
            }

        result.sendRedirect("index.htm");
        return null;
    }

}
