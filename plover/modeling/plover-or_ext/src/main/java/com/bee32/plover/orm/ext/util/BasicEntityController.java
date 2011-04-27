package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.free.UnexpectedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.util.EntityDto;

public abstract class BasicEntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends EntityController<E, K, Dto> {

    protected Integer _dtoSelection;
    protected boolean _createOTF;

    @Override
    protected ModelAndView _content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String _id = req.getParameter("id");
        K id = parseId(_id);

        E entity = dataManager.get(getEntityType(), id);
        if (entity == null)
            return Javascripts.alertAndBack("查阅的对象不存在。" //
                    + ClassUtil.getDisplayName(getEntityType()) + " [" + id + "]" //
            ).dump(req, resp);

        Dto dto = newDto(_dtoSelection);

        doAction(EntityAction.Load, entity, dto);

        ViewData view = new ViewData();
        view.entity = entity;
        view.dto = dto;
        view.put("it", dto);
        return view;
    }

    protected List<? extends E> __list() {
        return dataManager.loadAll(getEntityType());
    }

    @Override
    protected ModelAndView _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo tab = new DataTableDxo(req);

        List<? extends E> entityList = __list();

        int index = 0;
        for (E entity : entityList) {
            Dto item = newDto(_dtoSelection);

            doAction(EntityAction.Load, entity, item);

            tab.push(item.getId());
            tab.push(item.getVersion());

            fillDataRow(tab, item);

            tab.next();
            index++;
        }

        // assert index == entityList.size();
        tab.totalRecords = index;
        tab.totalDisplayRecords = tab.totalRecords;

        return JsonUtil.dump(resp, tab.exportMap());
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = view.isMethod("create");

        Dto dto = null;

        E entity = null;
        K id = null;

        if (!create) {
            String _id = req.getParameter("id");
            id = parseId(_id);

            entity = dataManager.get(getEntityType(), id);

            if (entity == null) {
                if (!_createOTF)
                    return Javascripts.alertAndBack("编辑的对象不存在。" + hint(entity, id)).dump(req, resp);

                create = true;

            } else {
                dto = newDto(_dtoSelection);

                doAction(EntityAction.Load, entity, dto);
            }
        }

        if (create) {
            entity = newEntity();

            if (id != null)
                EntityAccessor.setId(entity, id);

            dto = newDto(_dtoSelection);

            // Entity-initializors.
            doAction(EntityAction.Load, entity, dto);

            doAction(EntityAction.Create, entity, dto);
        }

        String _VERB = create ? "CREATE" : "MODIFY";
        view.put("_create", create);
        view.put("_verb", view.V.get(_VERB));

        view.entity = entity;
        view.dto = dto;
        view.put("it", dto);
        return view;
    }

    @Override
    protected ModelAndView _createOrEdit(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = view.isMethod("create");

        Dto dto = newDto(_dtoSelection);
        dto.parse(req);

        E entity = null;
        K id = null;

        if (!create) {
            id = dto.getId();
            if (id == null)
                throw new ServletException("id isn't specified");

            entity = dataManager.get(getEntityType(), id);
            if (entity == null) {
                if (!_createOTF)
                    return Javascripts.alertAndBack("对象尚未创建，无法保存。" + hint(entity, id) + "\n\n" //
                            + "这大概是有人在你编辑该对象的同时进行了删除操作引起的。\n" //
                            + "点击确定返回上一页。" //
                            + ClassUtil.getDisplayName(getEntityType()) + " [" + id + "]" //
                    ).dump(req, resp);

                create = true;
            }

            else {
                Integer requestVersion = dto.getVersion();
                if (requestVersion != null && requestVersion != entity.getVersion())
                    return Javascripts.alertAndBack("对象版本失效。" + hint(entity, id) + "\n\n" //
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

        doAction(EntityAction.Save, entity, dto);

        dataManager.saveOrUpdate(entity);

        view.entity = entity;
        view.dto = dto;
        return view;
    }

    protected void doAction(EntityAction action, E entity, Dto dto) {
        switch (action) {
        case Create:
            fillTemplate(dto);
            break;

        case Load:
            dto.marshal(entity);
            break;

        case Save:
            dto.unmarshalTo(this, entity);
            break;

        default:
            throw new UnexpectedException("Unknown entity action: " + action);
        }
    }

    protected abstract void fillDataRow(DataTableDxo tab, Dto dto);

    protected abstract void fillTemplate(Dto dto);

}
