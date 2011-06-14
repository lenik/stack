package com.bee32.sem.chance.facade;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.criterion.Restrictions;

import com.bee32.plover.arch.EnterpriseServiceFacade;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.chance.dao.ChanceActionDao;
import com.bee32.sem.chance.dao.ChanceDao;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;

public class ChanceServiceFacade
        extends EnterpriseServiceFacade {

    @Inject
    ChanceActionDao chanceActionDao;

    @Inject
    ChanceDao chanceDao;

    public List<ChanceActionDto> limitedChanceAction(int start, int pageSize) {
        List<ChanceAction> chanceActionList = chanceActionDao.limitedList(start, pageSize);
        return DTOs.marshalList(ChanceActionDto.class, chanceActionList);
    }

    public int getChanceActionCount() {
        return (int) chanceActionDao.count();
    }

    public int getChanceActionSearchCount(Date date_start, Date date_end) {
        return (int) chanceActionDao.count(Restrictions.between("beginTime", date_start, date_end));
    }

    public List<ChanceDto> limitedChance(int start, int pageSize) {
        List<Chance> chanceList = chanceDao.limitedList(start, pageSize);
        return DTOs.marshalList(ChanceDto.class, chanceList);
    }

    public List<ChanceDto> limitedChance(String keyword, int start, int pageSize) {
        List<Chance> chanceList = chanceDao.limitedList(keyword, start, pageSize);
        return DTOs.marshalList(ChanceDto.class, chanceList);
    }

    public List<ChanceDto> chanceSearch(String keyword) {
        List<Chance> chanceList = chanceDao.list();
        return DTOs.marshalList(ChanceDto.class, chanceList);
    }

    public int getChanceCount() {
        return (int) chanceDao.count();
    }

    public int getChanceCount(String keyword) {
        return (int) chanceDao.count(Restrictions.like("subject", keyword));
    }

}
