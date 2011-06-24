package com.bee32.sem.chance.web;

import java.util.HashMap;
import java.util.Map;

import javax.free.Strings;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
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
        tab.push(DateToRange.fullFormat.format(dto.getCreatedDate()));
        tab.push(dto.getParty());
        tab.push(dto.getStage().getLabel());
    }

    @Override
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
        result.put("categories", chanceCategoryDao.list());
        result.put("sources", chanceSourceTypeDao.list());
        result.put("owners", userDao.list());
    }

    @RequestMapping("chanceAdminjsf.do")
    public Map<String, ?> chanceActionAdminjsf(HttpServletRequest request, HttpServletResponse response) {
        return new HashMap<String, Object>();
    }

}
