package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import javax.servlet.ServletException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.util.EntityDto;

public abstract class CreateOrEditHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    protected boolean _createOTF;

    IEntityForming<E, K> forming;
    IPostUpdating<E, K> postUpdating;

    public CreateOrEditHandler(IEntityForming<E, K> forming) {
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
    public EntityActionResult handleRequest(EntityActionRequest req, EntityActionResult result)
            throws Exception {
        boolean create = req.methodEquals("create");

        Integer dtoSelection = eh.getSelection(SelectionMode.CREATE_EDIT);
        EntityDto<E, K> dto = eh.newDto(dtoSelection);

        dto.parse(this, req);

        E entity = null;
        K id = null;

        if (!create) {
            id = dto.getId();
            if (id == null)
                throw new ServletException("id isn't specified");

            entity = dataManager.get(eh.getEntityType(), id);
            if (entity == null) {
                if (!_createOTF)
                    return Javascripts.alertAndBack("对象尚未创建，无法保存。" + eh.getHint(id) + "\n\n" //
                            + "这大概是有人在你编辑该对象的同时进行了删除操作引起的。\n" //
                            + "点击确定返回上一页。" //
                            + ClassUtil.getDisplayName(eh.getEntityType()) + " [" + id + "]" //
                    ).dump(result);

                create = true;
            }

            else {
                Integer requestVersion = dto.getVersion();
                if (requestVersion != null && requestVersion != entity.getVersion())
                    return Javascripts.alertAndBack("对象版本失效。" + eh.getHint(id) + "\n\n" //
                            + "发生这个错误的原因是有人在你之前提交了这个对象的另一个版本，也可能是系统内部使这个对象的状态发生了改变。\n" //
                            + "点击确定返回上一页。" //
                    ).dump(result);
            }
        }

        if (create) {
            entity = eh.newEntity();

            if (id != null)
                EntityAccessor.setId(entity, id);
        }

        forming.saveForm(entity, dto);

        dataManager.saveOrUpdate(entity);

        if (postUpdating != null)
            postUpdating.postUpdate(entity);

        result.entity = entity;
        result.dto = dto;
        return result;
    }

}
