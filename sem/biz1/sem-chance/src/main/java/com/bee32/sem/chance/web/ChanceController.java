package com.bee32.sem.chance.web;

import java.io.IOException;

import javax.free.Strings;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.chance.dao.ChanceCategoryDao;
import com.bee32.sem.chance.dao.ChanceSourceTypeDao;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.util.DateToRange;

@RequestMapping(ChanceController.PREFIX)
public class ChanceController
        extends BasicEntityController<Chance, Long, ChanceDto> {

    public static final String PREFIX = "/customer/chance/";

    @Inject
    ChanceCategoryDao chanceCategoryDao;

    @Inject
    ChanceSourceTypeDao chanceSourceTypeDao;

    @Inject
    UserDao userDao;

    @Override
    protected void fillDataRow(DataTableDxo tab, ChanceDto dto) {
        tab.push(dto.getOwner().getName());
        tab.push(dto.getCategory().getLabel());
        tab.push(dto.getSource().getLabel());
        tab.push(dto.getSubject());
        tab.push(Strings.ellipse(dto.getContent(), 16));
        tab.push(DateToRange.fullFormat.format(dto.getCreateDate()));
        tab.push(dto.getParty());
        tab.push(dto.getStage().getLabel());
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        view.put("categories", chanceCategoryDao.list());
        view.put("sources", chanceSourceTypeDao.list());
        view.put("owners", userDao.list());
        return super._createOrEditForm(view, request, response);
    }

}
