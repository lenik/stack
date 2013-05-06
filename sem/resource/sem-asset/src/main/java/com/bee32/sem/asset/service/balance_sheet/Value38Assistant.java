package com.bee32.sem.asset.service.balance_sheet;

public class Value38Assistant {
    public static String buildQueryString(boolean verified) {
        StringBuilder sb = new StringBuilder();

        sb.append("select sum(a.native_value) ");
        sb.append("from account_ticket_item a ");
        sb.append("left join account_ticket b ");
        sb.append("on a.ticket=b.id ");
        sb.append("where (a.subject like '2232%') ");
        sb.append("and (b.begin_time<=:date) ");
        if(verified) {
            sb.append("     and b.verify_eval_state='33554433' ");
        }

        return sb.toString();
    }
}
