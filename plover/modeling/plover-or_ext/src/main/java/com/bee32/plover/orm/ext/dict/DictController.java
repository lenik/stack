package com.bee32.plover.orm.ext.dict;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.util.DTOs;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.ext.PloverORMExtModule;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.GenericEntityController;

@RequestMapping(DictController.PREFIX + "*")
public class DictController<E extends DictEntity<K>, D extends DictEntityDto<E, K>, K extends Serializable>
        extends GenericEntityController<E, D, K> {

    public static final String PREFIX = PloverORMExtModule.PREFIX + "1/";

    @Inject
    CommonDataManager dataManager;

    @Override
    protected ModelAndView _content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        K id = parseId(req.getParameter("id"));
        E entity = dataManager.load(getEntityType(), id);
        return it(newDto().marshal(entity));
    }

    @Override
    protected ModelAndView _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo tab = new DataTableDxo(req);

        List<? extends D> all = DTOs.marshalList(getDtoType(), dataManager.loadAll(getEntityType()));

        tab.totalRecords = all.size();
        tab.totalDisplayRecords = tab.totalRecords;

        for (D dto : all) {
            tab.push(dto.getId());
            tab.push(dto.getVersion());
            tab.push(dto.getName());
            tab.push(dto.getDescription());
            tab.next();
        }

        return JsonUtil.dump(resp, tab.exportMap());
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        boolean create = view.isMethod("create");

        view.put("_create", create);
        view.put("_verb", create ? "Create" : "Modify");

        D dto;
        if (create) {
            dto = newDto();
            dto.setName("");
            dto.setDescription("");
        } else {
            K id = parseId(req.getParameter("id"));
            E entity = dataManager.load(getEntityType(), id);
            dto = newDto().marshal(entity);
        }
        view.put("it", dto);

        return view;
    }

    @Override
    protected ModelAndView _createOrEdit(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = view.isMethod("create");

        D dto = newDto();
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
                throw new IllegalStateException("No entity whose id=" + id);

            Integer requestVersion = dto.getVersion();
            if (requestVersion != null && requestVersion != entity.getVersion()) {
                throw new IllegalStateException("Version obsoleted");
            }
        }

        dto.unmarshalTo(entity);
        dataManager.saveOrUpdate(entity);
        return view;
    }
}
