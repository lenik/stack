package com.bee32.plover.orm.ext.basic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.ext.util.EntityAction;
import com.bee32.plover.servlet.mvc.ResultView;

public abstract class CreateOrEditHandler
        extends EntityHandler {

    /**
     * Use <code>req.getAttribute("create"): Boolean</code> to distinguish create and update.
     */
    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception {
        boolean create = view.isMethod("create");

        Dto dto = newDto(_dtoSelection);
        dto.parse(this, req);

        E entity = null;
        K id = null;

        if (!create) {
            id = dto.getId();
            if (id == null)
                throw new ServletException("id isn't specified");

            entity = dataManager.get(getEntityType(), id);
            if (entity == null) {
                if (!_createOTF)
                    return Javascripts.alertAndBack("对象尚未创建，无法保存。" + hint(id) + "\n\n" //
                            + "这大概是有人在你编辑该对象的同时进行了删除操作引起的。\n" //
                            + "点击确定返回上一页。" //
                            + ClassUtil.getDisplayName(getEntityType()) + " [" + id + "]" //
                    ).dump(req, resp);

                create = true;
            }

            else {
                Integer requestVersion = dto.getVersion();
                if (requestVersion != null && requestVersion != entity.getVersion())
                    return Javascripts.alertAndBack("对象版本失效。" + hint(id) + "\n\n" //
                            + "发生这个错误的原因是有人在你之前提交了这个对象的另一个版本，也可能是系统内部使这个对象的状态发生了改变。\n" //
                            + "点击确定返回上一页。" //
                    ).dump(req, resp);
            }
        }

        if (create) {
            entity = newEntity();

            if (id != null)
                EntityAccessor.setId(entity, id);
        }

        doAction(EntityAction.SAVE, entity, dto);

        dataManager.saveOrUpdate(entity);

        doAction(EntityAction.POST_SAVE, entity, dto);

        view.entity = entity;
        view.dto = dto;
        return view;
    }

}
