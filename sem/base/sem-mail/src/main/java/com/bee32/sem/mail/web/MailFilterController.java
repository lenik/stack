package com.bee32.sem.mail.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.util.DTOs;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.EntityController;
import com.bee32.sem.mail.SEMMailModule;
import com.bee32.sem.mail.dao.MailBoxDao;
import com.bee32.sem.mail.dao.MailFilterDao;
import com.bee32.sem.mail.entity.MailFilter;

@RequestMapping(MailFilterController.PREFIX + "*")
public class MailFilterController
        extends EntityController<MailFilter, Integer, MailFilterDto> {

    public static final String PREFIX = SEMMailModule.PREFIX + "/filter/";

    @Inject
    MailFilterDao mailFilterDao;

    @Inject
    MailBoxDao mailBoxDao;

    @Override
    protected ModelAndView _content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        MailFilter entity = mailFilterDao.load(id);

        return it(new MailFilterDto(entity));
    }

    @Override
    public ModelAndView _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo tab = new DataTableDxo();
        tab.parse(req);

        List<MailFilterDto> all = DTOs.marshalList(MailFilterDto.class, //
                mailFilterDao.list());

        tab.totalRecords = all.size();
        tab.totalDisplayRecords = tab.totalRecords;

        for (MailFilterDto filter : all) {
            tab.push(filter.getId());
            tab.push(filter.getVersion());
            tab.push(filter.getName());
            tab.push(filter.getExpr());
            tab.push(filter.getSource() == null ? null : filter.getSource().getName());
            tab.push(filter.getTarget() == null ? null : filter.getTarget().getName());
            tab.next();
        }

        return JsonUtil.dump(resp, tab.exportMap());
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        boolean create = (Boolean) req.getAttribute("create");

        view.put("_create", create);
        view.put("_verb", create ? "Create" : "Modify");

        MailFilterDto dto;
        if (create) {
            dto = new MailFilterDto();
            dto.setName("");
            dto.setDescription("");
            dto.setExpr("");
            view.put("it", dto);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            MailFilter entity = mailFilterDao.load(id);
            dto = new MailFilterDto().marshal(entity);
        }
        view.put("it", dto);

        List<MailBoxDto> mailBoxes = DTOs.marshalList(MailBoxDto.class, 0, mailBoxDao.list());
        view.put("mailBoxes", mailBoxes);

        return view;
    }

    @Override
    protected ModelAndView _createOrEdit(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = view.isMethod("create");

        MailFilterDto dto = new MailFilterDto();
        dto.parse(req);

        MailFilter entity;
        if (create) {
            entity = new MailFilter();
        } else {
            Integer id = dto.getId();
            if (id == null)
                throw new ServletException("id isn't specified");

            entity = mailFilterDao.get(id);
            if (entity == null)
                throw new IllegalStateException("No allow list whose id=" + id);

            Integer requestVersion = dto.getVersion();
            if (requestVersion != null && requestVersion != entity.getVersion()) {
                throw new IllegalStateException("Version obsoleted");
            }
        }

        dto.unmarshalTo(entity);

        getAccessor().saveOrUpdate(entity);

        return view;
    }

}
