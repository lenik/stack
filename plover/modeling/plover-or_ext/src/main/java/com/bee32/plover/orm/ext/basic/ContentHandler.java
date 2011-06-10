package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public class ContentHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    IEntityForming<E, K> forming;

    public ContentHandler(IEntityForming<E, K> forming) {
        if (forming == null)
            throw new NullPointerException("forming");
        this.forming = forming;
    }

    @Override
    public EntityActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception {
        String _id = req.getParameter("id");
        K id = eh.parseRequiredId(_id);

        E entity = dataManager.get(eh.getEntityType(), id);
        if (entity == null)
            return Javascripts.alertAndBack("查阅的对象不存在。" //
                    + ClassUtil.getDisplayName(eh.getEntityType()) + " [" + id + "]" //
            ).dump(result);

        Integer dtoSelection = eh.getSelection(SelectionMode.INDEX);
        EntityDto<E, K> dto = eh.newDto(dtoSelection);

        forming.loadForm(entity, dto);

        result.entity = entity;
        result.dto = dto;
        result.put("it", dto);
        return result;
    }

}
