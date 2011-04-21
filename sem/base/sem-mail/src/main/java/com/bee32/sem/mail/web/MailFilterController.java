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
import com.bee32.plover.orm.util.DataTableDxo;
import com.bee32.plover.orm.util.EntityController;
import com.bee32.sem.mail.SEMMailModule;
import com.bee32.sem.mail.dao.MailBoxDao;
import com.bee32.sem.mail.dao.MailFilterDao;
import com.bee32.sem.mail.entity.MailFilter;

@RequestMapping(MailFilterController.PREFIX + "*")
public class MailFilterController
        extends EntityController<MailFilter, Integer> {

    public static final String PREFIX = SEMMailModule.PREFIX + "/filter/";

    @Inject
    MailFilterDao mailFilterDao;

    @Inject
    MailBoxDao mailBoxDao;

    @Override
    protected void preamble(Map<String, Object> metaData) {
        metaData.put(ENTITY_TYPE_NAME, "邮件过滤器");
    }

    @RequestMapping("content.htm")
    @Override
    public Map<String, Object> content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        MailFilter entity = mailFilterDao.load(id);

        MailFilterDto dto = new MailFilterDto().marshal(entity);

        ModelMap modelMap = new ModelMap();
        modelMap.put("it", dto);
        return modelMap;
    }

    @RequestMapping("data.htm")
    @Override
    public void data(HttpServletRequest req, HttpServletResponse resp)
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

        JsonUtil.dump(resp, tab.exportMap());
    }

    @Override
    protected Map<String, Object> form(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        boolean create = (Boolean) req.getAttribute("create");

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("_create", create);
        map.put("_verb", create ? "Create" : "Modify");

        MailFilterDto dto;
        if (create) {
            dto = new MailFilterDto();
            dto.setName("");
            dto.setDescription("");
            dto.setExpr("");
            map.put("it", dto);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            MailFilter entity = mailFilterDao.load(id);
            dto = new MailFilterDto().marshal(entity);
        }
        map.put("it", dto);

        List<MailBoxDto> mailBoxes = DTOs.marshalList(MailBoxDto.class, 0, mailBoxDao.list());
        map.put("mailBoxes", mailBoxes);

        return map;
    }

    @Override
    protected ModelAndView createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = (Boolean) req.getAttribute("create");

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

        dataManager.saveOrUpdate(entity);

        return new ModelAndView(viewOf("index"));
    }

}
