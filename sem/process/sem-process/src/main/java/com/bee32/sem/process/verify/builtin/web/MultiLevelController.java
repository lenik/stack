package com.bee32.sem.process.verify.builtin.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.arch.util.DTOs;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.Level;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.dao.LevelDao;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;

@RequestMapping(MultiLevelController.PREFIX + "*")
public class MultiLevelController
        extends BasicEntityController<MultiLevel, Integer, MultiLevelDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "level/";

    @Inject
    LevelDao levelDao;

    @Inject
    VerifyPolicyDao verifyPolicyDao;

    public MultiLevelController() {
        setDtoSelection(MultiLevelDto.LEVELS);
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
    protected void fillTemplate(MultiLevelDto dto) {
        dto.setName("");
        dto.setDescription("");
        dto.setLevels(new ArrayList<LevelDto>());
    }

    @Override
    protected void fillEntity(MultiLevel entity, MultiLevelDto dto) {
        entity.setName(dto.name);
        entity.setDescription(dto.description);

        List<Level> levels = entity.getLevels();
        List<Level> newLevels = new ArrayList<Level>();

        for (LevelDto newLevel : dto.getLevels()) {
            long newLimit = newLevel.getLimit();
            Level oldLevel = entity.getLevel(newLimit);

            int newPolicyId = newLevel.getTargetPolicyId();
            VerifyPolicy<?> newPolicy = verifyPolicyDao.load(newPolicyId);

            if (oldLevel != null) {
                int oldPolicyId = oldLevel.getTargetPolicy().getId();
                if (newPolicyId != oldPolicyId)
                    oldLevel.setTargetPolicy(newPolicy);
                newLevels.add(oldLevel);
            } else
                newLevels.add(new Level(entity, newLimit, newPolicy));
        }

        levels.clear();
        levels.addAll(newLevels);
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        super._createOrEditForm(view, req, resp);

        List<VerifyPolicyDto> allVerifyPolicies = DTOs.marshalList(VerifyPolicyDto.class, verifyPolicyDao.list());
        view.put("policies", allVerifyPolicies);

        return view;
    }

}
