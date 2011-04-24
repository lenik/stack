package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.util.DTOs;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.util.EntityDto;

public abstract class BasicEntityController<E extends EntityBean<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends EntityController<E, K, Dto> {

    Integer dtoSelection;

    protected void setDtoSelection(Integer dtoSelection) {
        this.dtoSelection = dtoSelection;
    }

    @Override
    protected ModelAndView _content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String _id = req.getParameter("id");
        K id = parseId(_id);

        E entity = dataManager.load(getEntityType(), id);

        Dto dto = newDto(dtoSelection);

        return it(dto.marshal(entity));
    }

    @Override
    protected ModelAndView _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo tab = new DataTableDxo(req);

        List<? extends E> entityList = dataManager.loadAll(getEntityType());

        Integer selection = dtoSelection;
        List<? extends Dto> list = DTOs.marshalList(getTransferType(), //
                selection.intValue(), entityList);

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

        view.put("_create", create);
        view.put("_verb", create ? "Create" : "Modify");

        Dto dto;
        if (create) {
            dto = newDto(dtoSelection);
            fillTemplate(dto);
        } else {
            String _id = req.getParameter("id");
            K id = parseId(_id);

            E entity = dataManager.load(getEntityType(), id);
            dto = newDto(dtoSelection).marshal(entity);
        }

        view.put("it", dto);
        return view;
    }

    @Override
    protected ModelAndView _createOrEdit(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = view.isMethod("create");

        Dto dto = newDto(dtoSelection);
        dto.parse(req);

        E entity;
        if (create) {
            entity = newEntity();
        } else {
            K id = dto.getId();
            if (id == null)
                throw new ServletException("id isn't specified");

            entity = dataManager.get(getEntityType(), id);
            if (entity == null)
                throw new IllegalStateException("No allow list whose id=" + id);

            Integer requestVersion = dto.getVersion();
            if (requestVersion != null && requestVersion != entity.getVersion()) {
                throw new IllegalStateException("Version obsoleted");
            }
        }

        fillEntity(entity, dto);

        dataManager.saveOrUpdate(entity);

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
        dto.unmarshalTo(entity);
    }

}
