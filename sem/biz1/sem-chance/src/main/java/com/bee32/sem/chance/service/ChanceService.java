package com.bee32.sem.chance.service;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.chance.dao.ChanceActionDao;
import com.bee32.sem.people.dao.PartyDao;
import com.bee32.sem.people.entity.Party;

public class ChanceService
        extends EnterpriseService
        implements IChanceService {

    @Inject
    UserDao userDao;

    @Inject
    ChanceActionDao chanceActionDao;

    @Inject
    PartyDao<Party> partyDao;

    @Override
    public int getAllPartyCount() {
        return (int) partyDao.count();
    }

    @Override
    public int getPartyCountByKeyword(String keyword) {
        return (int) partyDao.count(Restrictions.like("name", keyword));
    }

    @Override
    public List<Party> findLimitedParties(int displayStart, int displayLength) {
        return partyDao.limitedList(Party.class, displayStart, displayLength);
    }

    @Override
    public List<Party> findPartyByKeywords(String keyword, int displayStart, int displayLength) {
        return partyDao.limitedKeywordList(Party.class, keyword, displayStart, displayLength);
    }

}
