package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import org.springframework.dao.DataIntegrityViolationException;

import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;

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

        E entity = dataManager.fetch(eh.getEntityType(), id);
        if (entity == null)
            return Javascripts.alertAndBack("对象不存在: " + idString).dump(result);

        EntityFlags ef = EntityAccessor.getFlags(entity);
        if (ef.isLocked())
            return Javascripts.alertAndBack("对象被锁定: " + idString).dump(result);
        // if (ef.isDeleted())
        // return Javascripts.alertAndBack("对象被标记为删除: " + idString).dump(result);

        try {
            dataManager.delete(entity);
        } catch (DataIntegrityViolationException e) {
            return Javascripts.alertAndBack("不能删除正在使用中的对象。" + eh.getHint(id)).dump(result);
        }

        return result.sendRedirect("index.htm");
    }

}
