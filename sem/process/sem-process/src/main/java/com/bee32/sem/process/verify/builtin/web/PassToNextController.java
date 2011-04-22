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

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dao.PrincipalDao;
import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.util.DTOs;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.EntityController;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.PassStep;
import com.bee32.sem.process.verify.builtin.PassToNext;
import com.bee32.sem.process.verify.builtin.dao.PassStepDao;
import com.bee32.sem.process.verify.builtin.dao.PassToNextDao;

@RequestMapping(PassToNextController.PREFIX + "*")
public class PassToNextController
        extends EntityController<PassToNext, Integer> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/p2next/";

    @Inject
    PassToNextDao PassToNextDao;

    @Inject
    PassStepDao seqDao;

    @Inject
    PrincipalDao principalDao;

    @Override
    protected void preamble(Map<String, Object> metaData) {
        metaData.put(ENTITY_TYPE_NAME, "下一步策略");
    }

    @Override
    protected Map<String, Object> _content(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));

        PassToNext entity = PassToNextDao.load(id);

        PassToNextDto dto = new PassToNextDto(PassToNextDto.SEQUENCES).marshal(entity);

        ModelMap modelMap = new ModelMap();
        modelMap.put("it", dto);
        return modelMap;
    }

    @Override
    public void _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo tab = new DataTableDxo(req);

        List<PassToNextDto> all = DTOs.marshalList(PassToNextDto.class, //
                PassToNextDto.SEQUENCES, PassToNextDao.list());

        tab.totalRecords = all.size();
        tab.totalDisplayRecords = tab.totalRecords;

        for (PassToNextDto alist : all) {
            tab.push(alist.getId());
            tab.push(alist.getVersion());
            tab.push(alist.getName());
            tab.push(alist.getDescription());

            int max = 3;
            StringBuilder responsibles = null;
            for (PassStepDto seq : alist.getSequences()) {
                if (max <= 0) {
                    responsibles.append(", etc.");
                    break;
                }

                if (responsibles == null)
                    responsibles = new StringBuilder();
                else
                    responsibles.append(", ");

                responsibles.append(seq.getResponsible());

                max--;
            }
            tab.push(responsibles == null ? "" : responsibles.toString());
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

        PassToNextDto dto;
        if (create) {
            dto = new PassToNextDto(PassToNextDto.SEQUENCES);
            dto.setName("");
            dto.setDescription("");
            dto.setSequences(new ArrayList<PassStepDto>());
            map.put("it", dto);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            PassToNext entity = PassToNextDao.load(id);
            dto = new PassToNextDto(PassToNextDto.SEQUENCES).marshal(entity);
        }
        map.put("it", dto);

        return map;
    }

    @Override
    protected ModelAndView _createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = (Boolean) req.getAttribute("create");

        PassToNextDto dto = new PassToNextDto(PassToNextDto.SEQUENCES);
        dto.parse(req);

        PassToNext entity;
        if (create) {
            entity = new PassToNext();
        } else {
            Integer id = dto.getId();
            if (id == null)
                throw new ServletException("id isn't specified");

            entity = PassToNextDao.get(id);
            if (entity == null)
                throw new IllegalStateException("No pass-to-next whose id=" + id);

            Integer requestVersion = dto.getVersion();
            if (requestVersion != null && requestVersion != entity.getVersion()) {
                throw new IllegalStateException("Version obsoleted");
            }
        }

        { /* unmarshal */
            entity.setName(dto.name);
            entity.setDescription(dto.description);

            List<PassStep> sequences = new ArrayList<PassStep>();
            for (PassStepDto seq : dto.getSequences()) {
                int order = seq.getOrder();
                long responsibleId = seq.getResponsible().getId();
                boolean optional = seq.isOptional();

                Principal responsible = principalDao.load(responsibleId);

                PassStep seqEntity = new PassStep(entity, order, responsible, optional);

                sequences.add(seqEntity);
            }
            entity.setSequences(sequences);
        }

        dataManager.saveOrUpdate(entity);

        return new ModelAndView(viewOf("index"));
    }

}
