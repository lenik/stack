package com.bee32.sem.process.verify.builtin.web;

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
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.dao.LevelDao;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;
import com.bee32.sem.process.verify.builtin.dto.LevelDto;
import com.bee32.sem.process.verify.builtin.dto.MultiLevelDto;
import com.bee32.sem.process.verify.builtin.dto.AbstractVerifyPolicyDto;

@RequestMapping(MultiLevelController.PREFIX + "*")
public class MultiLevelController
        extends BasicEntityController<MultiLevel, Integer, MultiLevelDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "level/";

    @Inject
    LevelDao levelDao;

    @Inject
    VerifyPolicyDao verifyPolicyDao;

    public MultiLevelController() {
        _dtoSelection = MultiLevelDto.LEVELS;
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, MultiLevelDto multiLevel) {
        tab.push(multiLevel.getName());
        tab.push(multiLevel.getDescription());

        int max = 3;
        StringBuilder limits = null;
        for (LevelDto range : multiLevel.getLevels()) {
            if (max <= 0) {
                limits.append(", etc.");
                break;
            }

            if (limits == null)
                limits = new StringBuilder();
            else
                limits.append(", ");

            limits.append(range.getLimit());

            max--;
        }
        tab.push(limits == null ? "" : limits.toString());
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        super._createOrEditForm(view, req, resp);

        List<AbstractVerifyPolicyDto> allVerifyPolicies = DTOs.marshalList(AbstractVerifyPolicyDto.class, verifyPolicyDao.list());
        view.put("policies", allVerifyPolicies);

        return view;
    }

}
