package com.bee32.sem.process.verify.builtin.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.javascript.JavascriptChunk;
import com.bee32.plover.orm.util.EntityController;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyState;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.builtin.MultiLevelRange;
import com.bee32.sem.process.verify.builtin.dao.MultiLevelDao;
import com.bee32.sem.process.verify.builtin.dao.MultiLevelRangeDao;
import com.bee32.sem.process.verify.builtin.dao.VerifyPolicyDao;

@RequestMapping(MultiLevelController.PREFIX + "*")
public class MultiLevelController
        extends EntityController {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/level/";

    @Inject
    MultiLevelDao multiLevelDao;

    @Inject
    MultiLevelRangeDao rangeDao;

    @Inject
    VerifyPolicyDao verifyPolicyDao;

    @RequestMapping("content.htm")
    public Map<String, Object> content(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));

        MultiLevel entity = multiLevelDao.load(id);

        MultiLevelDto dto = new MultiLevelDto(MultiLevelDto.RANGES).marshal(entity);

        ModelMap modelMap = new ModelMap();
        modelMap.put("it", dto);
        return modelMap;
    }

    @RequestMapping("data.htm")
    public void data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DataTableDxo opts = new DataTableDxo();
        opts.parse(req);

        List<MultiLevelDto> all = MultiLevelDto.marshalList(//
                MultiLevelDto.RANGES, multiLevelDao.list());

        opts.totalRecords = all.size();
        opts.totalDisplayRecords = opts.totalRecords;

        List<Object[]> rows = new ArrayList<Object[]>();

        for (MultiLevelDto alist : all) {
            Object[] row = new Object[5 + 1];
            row[0] = alist.getId();
            row[1] = alist.getVersion();
            row[2] = alist.getName();
            row[3] = alist.getDescription();

            int max = 3;
            StringBuilder limits = null;
            for (MultiLevelRangeDto range : alist.getRanges()) {
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
            row[4] = limits == null ? "" : limits.toString();

            rows.add(row);
        }

        opts.data = rows;

        JsonUtil.dump(resp, opts.exportMap());
    }

    @Override
    protected Map<String, Object> form(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        boolean create = (Boolean) req.getAttribute("create");

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("_create", create);
        map.put("_verb", create ? "Create" : "Modify");

        MultiLevelDto dto;
        if (create) {
            dto = new MultiLevelDto(MultiLevelDto.RANGES);
            dto.setName("");
            dto.setDescription("");
            dto.setRanges(new ArrayList<MultiLevelRangeDto>());
            map.put("it", dto);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            MultiLevel entity = multiLevelDao.load(id);
            dto = new MultiLevelDto(MultiLevelDto.RANGES).marshal(entity);
        }
        map.put("it", dto);

        return map;
    }

    @Override
    protected ModelAndView createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean create = (Boolean) req.getAttribute("create");

        MultiLevelDto dto = new MultiLevelDto(MultiLevelDto.RANGES);
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

            Set<MultiLevelRange> ranges = new HashSet<MultiLevelRange>();
            for (MultiLevelRangeDto range : dto.getRanges()) {
                long limit = range.getLimit();
                int targetPolicyId = range.getTargetPolicyId();

                VerifyPolicy<?, VerifyState> targetPolicy = verifyPolicyDao.load(targetPolicyId);

                MultiLevelRange rangeEntity = new MultiLevelRange(entity, limit, targetPolicy);

                ranges.add(rangeEntity);
            }
            entity.setRanges(ranges);
        }

        dataManager.saveOrUpdate(entity);

        return new ModelAndView(viewOf("index"));
    }

    @RequestMapping("delete.htm")
    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));

        MultiLevel MultiLevel = multiLevelDao.get(id);
        if (MultiLevel != null)
            try {
                dataManager.delete(MultiLevel);
            } catch (DataIntegrityViolationException e) {
                resp.setCharacterEncoding("utf-8");

                PrintWriter out = resp.getWriter();

                String message = "分级审核策略 " + MultiLevel.getName() + " 正在被其它对象使用中，删除失败。";

                JavascriptChunk chunk = new JavascriptChunk();
                chunk.println("alert('" + HtmlUtils.htmlEscape(message) + "'); ");
                chunk.println("history.back(); ");
                chunk.dump(req, resp);

                return null;
            }

        return viewOf("index");
    }

}
