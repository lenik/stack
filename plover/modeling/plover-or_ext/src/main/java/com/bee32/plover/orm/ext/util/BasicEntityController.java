package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.DTOs;
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
        dto.marshal(entity);

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

        Integer selection = _dtoSelection;
        List<? extends Dto> list = DTOs.marshalList(getTransferType(), //
                selection == null ? null : selection.intValue(), entityList);

        tab.totalRecords = list.size();
        tab.totalDisplayRecords = tab.totalRecords;

        for (Dto item : list) {
            tab.push(item.getId());
            tab.push(item.getVersion());

            fillDataRow(tab, item);

            tab.next();
        }

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

            } else
                dto = newDto(_dtoSelection).marshal(entity);
        }

        if (create) {
            entity = newEntity();

            if (id != null)
                EntityAccessor.setId(entity, id);

            dto = newDto(_dtoSelection).marshal(entity);

            fillTemplate(dto);
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
                            + "这大概是有人在你编辑该对象的时候进行了删除操作引起的。\n" //
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

        fillEntity(entity, dto);

        dataManager.saveOrUpdate(entity);

        view.entity = entity;
        view.dto = dto;
        return view;
    }

    protected abstract void fillDataRow(DataTableDxo tab, Dto dto);

    protected abstract void fillTemplate(Dto dto);

    /**
     * Fill the entity with parameters comes from dto.
     *
     * The entity is unmarshalled from the dto in default implementattion.
     *
     * @param entity
     *            The entity which would be filled.
     * @param dto
     *            The incoming dto.
     */
    protected void fillEntity(E entity, Dto dto) {
        dto.unmarshalTo(this, entity);
    }

}
