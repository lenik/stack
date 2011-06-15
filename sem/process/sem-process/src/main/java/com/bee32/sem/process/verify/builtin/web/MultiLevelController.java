package com.bee32.sem.process.verify.builtin.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.dao.LevelDao;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;
import com.bee32.sem.process.verify.builtin.dto.LevelDto;
import com.bee32.sem.process.verify.builtin.dto.MultiLevelDto;
import com.bee32.sem.process.verify.builtin.dto.VerifyPolicyDto;

@RequestMapping(MultiLevelController.PREFIX + "*")
public class MultiLevelController
        extends BasicEntityController<MultiLevel, Integer, MultiLevelDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "level/";

    @Inject
    LevelDao levelDao;

    @Inject
    VerifyPolicyDao verifyPolicyDao;

    @Override
    protected void fillDataRow(DataTableDxo tab, MultiLevelDto multiLevel) {
        tab.push(multiLevel.getLabel());
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
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
        List<VerifyPolicyDto> allVerifyPolicies = DTOs.marshalList(VerifyPolicyDto.class, verifyPolicyDao.list());
        result.put("policies", allVerifyPolicies);
    }

}
