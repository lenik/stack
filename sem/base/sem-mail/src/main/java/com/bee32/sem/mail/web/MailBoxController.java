package com.bee32.sem.mail.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
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

    @Override
    protected void preamble(Map<String, Object> metaData) {
        metaData.put(ENTITY_TYPE_NAME, "邮箱管理");
    }

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
    public Map<String, Object> content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        MailBox entity = mailBoxDao.load(id);

        MailBoxDto dto = new MailBoxDto().marshal(entity);

        ModelMap modelMap = new ModelMap();
        modelMap.put("it", dto);
        return modelMap;
    }

    @RequestMapping("data.htm")
    @Override
    public void _data(HttpServletRequest req, HttpServletResponse resp)
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

        JsonUtil.dump(resp, tab.exportMap());
    }

    @Override
    protected Map<String, Object> _createOrEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        boolean create = (Boolean) req.getAttribute("create");

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("_create", create);
        map.put("_verb", create ? "Create" : "Modify");

        MailBoxDto dto;
        if (create) {
            dto = new MailBoxDto();
            dto.setName("");
            map.put("it", dto);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            MailBox entity = mailBoxDao.load(id);
            dto = new MailBoxDto().marshal(entity);
        }
        map.put("it", dto);

        return map;
    }

    @Override
    protected ModelAndView _createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = (Boolean) req.getAttribute("create");

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
