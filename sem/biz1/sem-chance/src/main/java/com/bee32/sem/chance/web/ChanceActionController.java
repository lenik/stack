package com.bee32.sem.chance.web;

import java.util.HashMap;
import java.util.Map;

import javax.free.Strings;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.icsf.principal.UserDao;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.chance.dao.ChanceActionStyleDao;
import com.bee32.sem.chance.dao.ChanceDao;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.people.dto.PartyDto;

@RequestMapping(ChanceActionController.PREFIX)
public class ChanceActionController
        extends BasicEntityController<ChanceAction, Long, ChanceActionDto> {

    public static final String PREFIX = "/customer/chanceAction/";

    @Inject
    ChanceActionStyleDao actionStyleDao;

    @Inject
    ChanceDao chanceDao;

    @Inject
    UserDao userDao;

// @Override
// protected void fillSearchModel(SearchModel model, TextMap request)
// throws ParseException {
// super.fillSearchModel(model, request);
//
// String sDate = request.getString("date");
// Long userId = request.getNLong("userId");
// if (sDate == null)
// throw new NullPointerException("sDate in fillSearchModel is null");
// Date date = null;
// try {
// date = DateToRange.fullFormat.parse(sDate);
// } catch (java.text.ParseException e) {
// throw new RuntimeException(e.getMessage(), e);
// }
// User actor = userDao.load(userId);
// Map<String, Date> dateRangeMap = DateToRange.dateToMonthRange(date);
// model.add(Restrictions.eq("actor", actor));
// model.add(Restrictions.between("beginDate", dateRangeMap.get("begin_month"),
// dateRangeMap.get("end_month")));
//
// }

    @RequestMapping("chanceActionAdminjsf.do")
    public Map<String, ?> chanceActionAdminjsf(HttpServletRequest request, HttpServletResponse response) {
        return new HashMap<String, Object>();
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, ChanceActionDto dto) {

        // 日期
        tab.push(dto.getDate());

        // 时间段处理
        tab.push(dto.getTimeRange());

        // 类型
        String chanceActionType = dto.isPlan() == true ? "计划" : "日志";
        tab.push(chanceActionType);

        String style = dto.getStyle() == null ? "" : dto.getStyle().getLabel();
        tab.push(style);

        // 对应客户
        String customer = null;
        for (PartyDto party : dto.getParties()) {
            if (customer == null)
                customer = party.getName();
            else
                customer = customer + "," + party.getName();
        }
        tab.push(customer);

        // 行动简略
        tab.push(dto.getSubject());

        // 费用简略
        tab.push(Strings.ellipse(dto.getSpending(), 15));

        // 对应机会 如果存在的话
        String chanceSubject = dto.getChance() == null ? "" : Strings.ellipse(dto.getChance().getSubject(), 16);
        tab.push(chanceSubject);

        // 机会阶段
        String chanceStage = dto.getStage() == null ? "" : dto.getStage().getLabel();
        tab.push(chanceStage);
    }

    @Override
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
        result.put("stages", actionStyleDao.list());
        result.put("chances", chanceDao.list());
        result.put("actors", userDao.list());
    }

// @RequestMapping("search.do")
// public void search(HttpServletRequest request, HttpServletResponse response)
// throws IOException, ServletException {
//
// DataTableDxo tab = new DataTableDxo(request);
// String keywords = request.getParameter("partyName");
//
// List<Party> partyList;
// int count;
//
// if (keywords == null || keywords.length() == 0) {
// partyList = chanceService.limitedPartyList(tab.displayStart, tab.displayLength);
// count = chanceService.getPartyCount();
// } else {
// partyList = chanceService.limitedPartyKeywordList(keywords.trim(), tab.displayStart,
// tab.displayLength);
// count = chanceService.limitedPartyKeywordListCount(keywords);
// }
//
// tab.totalRecords = count;
// tab.totalDisplayRecords = count;
//
// for (Party party : partyList) {
// tab.push(party.getName());
// List<Contact> contact = party.getContacts();
// tab.push(contact.get(0).getAddress());
// tab.push(contact.get(0).getFax());
// tab.push(contact.get(0).getTel());
// tab.push(contact.get(0).getWebsite());
// tab.next();
// }
//
// JsonUtil.dump(response, tab.exportMap());
// }
}
