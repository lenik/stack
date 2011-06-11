package com.bee32.sem.mail.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.mail.SEMMailModule;
import com.bee32.sem.mail.dao.MailFilterDao;
import com.bee32.sem.mail.dao.MailFolderDao;
import com.bee32.sem.mail.dto.MailFilterDto;
import com.bee32.sem.mail.dto.MailFolderDto;
import com.bee32.sem.mail.entity.MailFilter;

@RequestMapping(MailFilterController.PREFIX + "*")
public class MailFilterController
        extends BasicEntityController<MailFilter, Integer, MailFilterDto> {

    public static final String PREFIX = SEMMailModule.PREFIX + "/filter/";

    @Inject
    MailFilterDao mailFilterDao;

    @Inject
    MailFolderDao mailBoxDao;

    @Override
    protected void fillDataRow(DataTableDxo tab, MailFilterDto filter) {
        tab.push(filter.getName());
        tab.push(filter.getExpr());
        tab.push(filter.getSource() == null ? null : filter.getSource().getName());
        tab.push(filter.getTarget() == null ? null : filter.getTarget().getName());
    }

    @Override
    protected void fillFormExtra(ActionResult result) {
        List<MailFolderDto> mailBoxes = DTOs.marshalList(MailFolderDto.class, 0, mailBoxDao.list());
        result.put("mailBoxes", mailBoxes);
    }

}
