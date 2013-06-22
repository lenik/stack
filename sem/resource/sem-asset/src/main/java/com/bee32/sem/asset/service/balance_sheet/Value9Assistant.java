package com.bee32.sem.asset.service.balance_sheet;

public class Value9Assistant {
    public static String buildQueryString(boolean verified) {
        StringBuilder sb = new StringBuilder();

        sb.append("select sum(a.native_value) ");
        sb.append("from account_ticket_item a ");
        sb.append("left join account_ticket b ");
        sb.append("on a.ticket=b.id ");
        sb.append("left join account_init c ");
        sb.append("on a.init=c.id ");
        sb.append("where (a.subject like '1401%' or a.subject like '1402%' or a.subject like '1403%' or a.subject like '1404%' or a.subject like '1405%' or a.subject like '1407%' or a.subject like '1408%' or a.subject like '1411%' or a.subject like '1421%' or a.subject like '4001%' or a.subject like '4101%') ");
        sb.append("and ((b.begin_time<=:date) or (c.begin_time<=:date)) ");
        if(verified) {
            sb.append("     and (b.verify_eval_state='33554433' and c.verify_eval_state='33554433') ");
        }

        return sb.toString();
    }
}
