package com.bee32.sem.process.verify.builtin.web;

import java.io.IOException;
import java.util.ArrayList;
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
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.Level;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.dao.LevelDao;
import com.bee32.sem.process.verify.builtin.dao.MultiLevelDao;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;

@RequestMapping(MultiLevelController.PREFIX + "*")
public class MultiLevelController
        extends EntityController<MultiLevel, Integer> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/level/";

    @Inject
    MultiLevelDao multiLevelDao;

    @Inject
    LevelDao levelDao;

    @Inject
    VerifyPolicyDao verifyPolicyDao;

    @Override
    protected void preamble(Map<String, Object> metaData) {
        metaData.put(ENTITY_TYPE_NAME, "分级审核策略");
    }

    @Override
    protected Map<String, Object> _content(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));

        MultiLevel entity = multiLevelDao.load(id);

        MultiLevelDto dto = new MultiLevelDto(MultiLevelDto.LEVELS).marshal(entity);

        ModelMap modelMap = new ModelMap();
        modelMap.put("it", dto);
        return modelMap;
    }

    @Override
    protected void _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo tab = new DataTableDxo(req);

        List<MultiLevelDto> all = DTOs.marshalList(MultiLevelDto.class, //
                MultiLevelDto.LEVELS, multiLevelDao.list());

        tab.totalRecords = all.size();
        tab.totalDisplayRecords = tab.totalRecords;

        for (MultiLevelDto alist : all) {
            tab.push(alist.getId());
            tab.push(alist.getVersion());
            tab.push(alist.getName());
            tab.push(alist.getDescription());

            int max = 3;
            StringBuilder limits = null;
            for (LevelDto range : alist.getLevels()) {
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

        MultiLevelDto dto;
        if (create) {
            dto = new MultiLevelDto(MultiLevelDto.LEVELS);
            dto.setName("");
            dto.setDescription("");
            dto.setLevels(new ArrayList<LevelDto>());
            map.put("it", dto);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            MultiLevel entity = multiLevelDao.load(id);
            dto = new MultiLevelDto(MultiLevelDto.LEVELS).marshal(entity);
        }
        map.put("it", dto);

        List<VerifyPolicyDto> allVerifyPolicies = DTOs.marshalList(VerifyPolicyDto.class, verifyPolicyDao.list());
        map.put("policies", allVerifyPolicies);

        return map;
    }

    @Override
    protected ModelAndView _createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = (Boolean) req.getAttribute("create");

        MultiLevelDto dto = new MultiLevelDto(MultiLevelDto.LEVELS);
        dto.parse(req);

        MultiLevel entity;
        if (create) {
            entity = new MultiLevel();
        } else {
            Integer id = dto.getId();
            if (id == null)
                throw new ServletException("id isn't specified");

            entity = multiLevelDao.get(id);
            if (entity == null)
                throw new IllegalStateException("No multi-level whose id=" + id);

            Integer requestVersion = dto.getVersion();
            if (requestVersion != null && requestVersion != entity.getVersion()) {
                throw new IllegalStateException("Version obsoleted");
            }
        }

        { /* unmarshal */
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

        dataManager.saveOrUpdate(entity);

        return new ModelAndView(viewOf("index"));
    }

}
