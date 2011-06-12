package com.bee32.sem.mail.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.mail.SEMMailModule;
import com.bee32.sem.mail.dao.MailDao;
import com.bee32.sem.mail.dao.MailFolderDao;
import com.bee32.sem.mail.dto.MailFolderDto;
import com.bee32.sem.mail.entity.MailFolder;

@RequestMapping(MailFolderController.PREFIX + "*")
public class MailFolderController
        extends BasicEntityController<MailFolder, Integer, MailFolderDto> {

    public static final String PREFIX = SEMMailModule.PREFIX + "/folder/";

    @Inject
    MailFolderDao mailFolderDao;

    @Inject
    MailDao mailDao;

    @RequestMapping("inbox.do")
    public ModelAndView inbox(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return mailbox(req, resp);
    }

    @RequestMapping("outbox.do")
    public ModelAndView outbox(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return mailbox(req, resp);
    }

    @RequestMapping("trash.do")
    public ModelAndView trash(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return mailbox(req, resp);
    }

    ModelAndView mailbox(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ModelAndView model = new ModelAndView("content");
        return model;
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, MailFolderDto dto) {
        tab.push(dto.getName());
    }

}
