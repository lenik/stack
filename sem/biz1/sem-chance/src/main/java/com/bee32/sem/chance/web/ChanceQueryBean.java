package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.ChanceDto;

public class ChanceQueryBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    Date limitDateFrom;
    Date limitDateTo;

    boolean allSalesman;

    List<String> selectedSalesmansToQuery;
    List<UserDto> salesmansToQuery = new ArrayList<UserDto>();

    public ChanceQueryBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();
    }

    public Date getLimitDateFrom() {
        return limitDateFrom;
    }

    public void setLimitDateFrom(Date limitDateFrom) {
        this.limitDateFrom = limitDateFrom;
    }

    public Date getLimitDateTo() {
        return limitDateTo;
    }

    public void setLimitDateTo(Date limitDateTo) {
        this.limitDateTo = limitDateTo;
    }

    public boolean isAllSalesman() {
        return allSalesman;
    }

    public void setAllSalesman(boolean allSalesman) {
        this.allSalesman = allSalesman;
    }

    public List<String> getSelectedSalesmansToQuery() {
        return selectedSalesmansToQuery;
    }

    public void setSelectedSalesmansToQuery(
            List<String> selectedSalesmansToQuery) {
        this.selectedSalesmansToQuery = selectedSalesmansToQuery;
    }

    public List<UserDto> getSalesmansToQuery() {
        return salesmansToQuery;
    }

    public void setSalesmansToQuery(List<UserDto> salesmansToQuery) {
        this.salesmansToQuery = salesmansToQuery;
    }



    public void removeSalesmanToQuery() {

    }

    public List<ChanceDto> getChances() {
        return null;
    }

}
