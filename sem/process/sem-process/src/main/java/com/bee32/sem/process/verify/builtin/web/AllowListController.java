package com.bee32.sem.process.verify.builtin.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.icsf.principal.dao.PrincipalDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.AllowList;
import com.bee32.sem.process.verify.builtin.dto.AllowListDto;

@RequestMapping(AllowListController.PREFIX + "*")
public class AllowListController
        extends BasicEntityController<AllowList, Integer, AllowListDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "list/";

    @Inject
    PrincipalDao principalDao;

    @Inject
    UserDao userDao;

    public AllowListController() {
        _dtoSelection = AllowListDto.RESPONSIBLES;
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, AllowListDto item) {
        tab.push(item.getLabel());
        tab.push(item.getDescription());

        int max = 3;
        StringBuilder names = null;
        for (PrincipalDto responsible : item.getResponsibles()) {
            if (max <= 0) {
                names.append(", etc.");
                break;
            }

            if (names == null)
                names = new StringBuilder();
            else
                names.append(", ");

            names.append(responsible.getDisplayName());

            max--;
        }
        tab.push(names == null ? "" : names.toString());
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        super._createOrEditForm(view, req, resp);

        List<UserDto> users = DTOs.marshalList(UserDto.class, 0, userDao.list());
        view.addObject("users", users);

        return view;
    }

}
