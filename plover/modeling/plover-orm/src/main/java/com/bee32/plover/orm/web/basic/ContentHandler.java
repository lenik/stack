package com.bee32.plover.orm.web.basic;

import java.io.Serializable;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.orm.web.IEntityForming;
import com.bee32.plover.orm.web.SelectionMode;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

public class ContentHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    IEntityForming<E, K> forming;

    public ContentHandler(Class<E> entityType, IEntityForming<E, K> forming) {
        super(entityType);

        if (forming == null)
            throw new NullPointerException("forming");

        this.forming = forming;
    }

    @Override
    protected ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws Exception {
        String _id = req.getParameter("id");
        K id = eh.parseRequiredId(_id);

        E entity = ctx.data.access(eh.getEntityType()).get(id);
        if (entity == null)
            return Javascripts.alertAndBack("查阅的对象不存在。" //
                    + ClassUtil.getTypeName(eh.getEntityType()) + " [" + id + "]" //
            ).dump(result);

        Integer dtoSelection = eh.getSelection(SelectionMode.INDEX);
        EntityDto<E, K> dto = eh.newDto(dtoSelection);

        forming.loadForm(entity, dto);

        result.put("entity", entity);
        result.put("it", dto);
        return result;
    }

}
