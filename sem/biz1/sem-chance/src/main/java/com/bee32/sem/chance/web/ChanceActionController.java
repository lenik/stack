package com.bee32.sem.chance.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.Strings;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.chance.dao.ChanceActionStyleDao;
import com.bee32.sem.chance.dao.ChanceDao;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.service.IChanceService;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonContact;

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

    @Inject
    IChanceService chanceService;

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

    @RequestMapping("chanceActionAdminjsf")
    public Map<String, ?> chanceActionAdminjsf(HttpServletRequest request, HttpServletResponse response){
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
        tab.push(Strings.ellipse(dto.getContent(), 15));

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
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        view.put("stages", actionStyleDao.list());
        view.put("chances", chanceDao.list());
        view.put("actors", userDao.list());
        return super._createOrEditForm(view, request, response);
    }

    @RequestMapping("search.htm")
    public void search(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        DataTableDxo tab = new DataTableDxo(request);
        String keywords = request.getParameter("partyName");

        List<Party> partyList;
        int count;

        if (keywords == null || keywords.length() == 0) {
            partyList = chanceService.findLimitedParties(tab.displayStart, tab.displayLength);
            count = chanceService.getAllPartyCount();
        } else {
            partyList = chanceService.findPartyByKeywords(keywords.trim(), tab.displayStart, tab.displayLength);
            count = chanceService.getPartyCountByKeyword(keywords);
        }

        tab.totalRecords = count;
        tab.totalDisplayRecords = count;

        for (Party party : partyList) {
            tab.push(party.getName());
            if (party instanceof Person) {
                Person person = (Person) party;
                PersonContact contact = person.getContacts().get(0);
                tab.push(contact.getAddress());
                tab.push(contact.getFax());
                tab.push(contact.getTel());
                tab.push(contact.getWebsite());
            }
            tab.next();
        }

        JsonUtil.dump(response, tab.exportMap());
    }

}
