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
import com.bee32.sem.mail.dao.MailDao;
import com.bee32.sem.mail.entity.MailBox;

@RequestMapping(MailBoxController.PREFIX + "*")
public class MailBoxController
        extends EntityController<MailBox, Integer> {

    public static final String PREFIX = SEMMailModule.PREFIX + "/mailbox/";

    @Inject
    MailBoxDao mailBoxDao;
    @Inject
    MailDao mailDao;

    @RequestMapping("inbox.htm")
    public ModelAndView inbox(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return mailbox(req, resp);
    }

    @RequestMapping("outbox.htm")
    public ModelAndView outbox(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return mailbox(req, resp);
    }

    @RequestMapping("trash.htm")
    public ModelAndView trash(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return mailbox(req, resp);
    }

    ModelAndView mailbox(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ModelAndView model = new ModelAndView("content");
        return model;
    }

    @RequestMapping("content.htm")
    @Override
    public ModelAndView content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        MailBox entity = mailBoxDao.load(id);

        return it(new MailBoxDto().marshal(entity));
    }

    @RequestMapping("data.htm")
    @Override
    public ModelAndView _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo tab = new DataTableDxo();
        tab.parse(req);

        List<MailBoxDto> all = DTOs.marshalList(MailBoxDto.class, //
                mailBoxDao.list());

        tab.totalRecords = all.size();
        tab.totalDisplayRecords = tab.totalRecords;

        for (MailBoxDto filter : all) {
            tab.push(filter.getId());
            tab.push(filter.getVersion());
            tab.push(filter.getName());
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

        MailBoxDto dto;
        if (create) {
            dto = new MailBoxDto();
            dto.setName("");
            view.put("it", dto);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            MailBox entity = mailBoxDao.load(id);
            dto = new MailBoxDto().marshal(entity);
        }
        view.put("it", dto);

        return view;
    }

    @Override
    protected ModelAndView _createOrEdit(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = view.isMethod("create");

        MailBoxDto dto = new MailBoxDto();
        dto.parse(req);

        MailBox entity;
        if (create) {
            entity = new MailBox();
        } else {
            Integer id = dto.getId();
            if (id == null)
                throw new ServletException("id isn't specified");

            entity = mailBoxDao.get(id);
            if (entity == null)
                throw new IllegalStateException("No allow list whose id=" + id);

            Integer requestVersion = dto.getVersion();
            if (requestVersion != null && requestVersion != entity.getVersion()) {
                throw new IllegalStateException("Version obsoleted");
            }
        }

        { /* unmarshal */
            entity.setName(dto.name);
        }

        dataManager.saveOrUpdate(entity);

        return new ModelAndView(viewOf("index"));
    }

}
