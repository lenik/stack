package com.bee32.sem.asset.service.profit_sheet;

public class ProfitValue27Assistant {
    public static String buildQueryString(boolean verified) {
        StringBuilder sb = new StringBuilder();

        sb.append("select sum(a.native_value) ");
        sb.append("from account_ticket_item a ");
        sb.append("left join account_ticket b ");
        sb.append("on a.ticket=b.id ");
        sb.append("where (a.subject like '571103%') ");
        sb.append("and (a.debit_side is true) and (b.begin_time>=:beginDate and b.begin_time<=:endDate) ");
        if(verified) {
            sb.append("     and b.verify_eval_state='33554433' ");
        }

        return sb.toString();
    }
}
