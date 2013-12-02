package com.bee32.sem.salary.service;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.asset.service.IAccountTicketSourceProvider;
import com.bee32.sem.attendance.util.SalaryCriteria;
import com.bee32.sem.salary.entity.MonthSalary;

/**
 * 月工资原始单据提供者
 */
public class MonthSalarySourceProvider
        extends DataService
        implements IAccountTicketSourceProvider {

    /**
     * 取得原始单据
     *
     * 根据凭证取得对应的月度总工资。
     *
     * @param ticket
     * @return
     */
    @Override
    public IAccountTicketSource getSource(long ticketId) {

        MonthSalary monthSalary = DATA(MonthSalary.class).getUnique(//
                SalaryCriteria.correspondingTicket(ticketId));

        return (IAccountTicketSource) monthSalary;
    }

}
