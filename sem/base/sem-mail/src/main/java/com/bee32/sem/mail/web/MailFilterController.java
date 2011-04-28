package com.bee32.sem.mail.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.mail.SEMMailModule;
import com.bee32.sem.mail.dao.MailBoxDao;
import com.bee32.sem.mail.dao.MailFilterDao;
import com.bee32.sem.mail.dto.MailBoxDto;
import com.bee32.sem.mail.dto.MailFilterDto;
import com.bee32.sem.mail.entity.MailFilter;

@RequestMapping(MailFilterController.PREFIX + "*")
public class MailFilterController
        extends BasicEntityController<MailFilter, Integer, MailFilterDto> {

    public static final String PREFIX = SEMMailModule.PREFIX + "/filter/";

    @Inject
    MailFilterDao mailFilterDao;

    @Inject
    MailBoxDao mailBoxDao;

    @Override
    protected void fillDataRow(DataTableDxo tab, MailFilterDto filter) {
        tab.push(filter.getName());
        tab.push(filter.getExpr());
        tab.push(filter.getSource() == null ? null : filter.getSource().getName());
        tab.push(filter.getTarget() == null ? null : filter.getTarget().getName());
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super._createOrEditForm(view, req, resp);

        List<MailBoxDto> mailBoxes = DTOs.marshalList(MailBoxDto.class, 0, mailBoxDao.list());
        view.put("mailBoxes", mailBoxes);

        return view;
    }

}
