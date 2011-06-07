package com.bee32.sem.chance.web;

import javax.free.Strings;
import javax.inject.Inject;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.service.IChanceService;
import com.bee32.sem.chance.util.DateToRange;
import com.bee32.sem.people.dto.PartyDto;

public class ChanceActionController
        extends BasicEntityController<ChanceAction, Long, ChanceActionDto> {

    public static final String CHANCEACTION = "/customer/chanceAction/";

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

    @Override
    protected void fillDataRow(DataTableDxo tab, ChanceActionDto dto) {

        // 日期
        tab.push(DateToRange.fullFormat.format(dto.getBeginTime()).subSequence(0, 9));
        // 时间段处理
        String timeRange = DateToRange.fullFormat.format(dto.getBeginTime()).substring(10) + " 到 "
                + DateToRange.fullFormat.format(dto.getEndTime()).substring(10);
        tab.push(timeRange);

        // 销售行动类型
        String chanceActionType = dto.isPlan() == true ? "行动计划" : "行动日志";
        tab.push(chanceActionType);

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
        String chanceSubject = dto.getChance() == null ? "" : dto.getChance().getSubject();
        tab.push(chanceSubject);

        // 机会阶段
        String chanceStage = dto.getStage() == null ? "" : dto.getStage().getDescription();
        tab.push(chanceStage);
    }
}
