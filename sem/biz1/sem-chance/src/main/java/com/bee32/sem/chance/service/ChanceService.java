package com.bee32.sem.chance.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.chance.dao.ChanceActionDao;
import com.bee32.sem.chance.dao.ChanceDao;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.people.dao.PartyDao;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.user.util.SessionLoginInfo;

public class ChanceService
        extends EnterpriseService
        implements IChanceService {

    @Inject
    UserDao userDao;

    @Inject
    ChanceActionDao chanceActionDao;

    @Inject
    ChanceDao chanceDao;

    @Inject
    PartyDao<Party> partyDao;

// @Override
// public int getPartyCount() {
// return (int) partyDao.count();
// }
//
// @Override
// public List<Party> limitedPartyList(int first, int pageSize){
// return null;
// }

// @Override
// public int limitedPartyKeywordListCount(String keyword) {
// return (int) partyDao.count(Restrictions.like("name", "%" + keyword + "%"));
// }
//
// @Override
// public List<Party> limitedPartyKeywordList(String keyword, int displayStart, int displayLength) {
// return partyDao.limitedKeywordList(Party.class, keyword, displayStart, displayLength);
// }

    @Override
    public int getChanceActionCount() {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return (int) chanceActionDao.count(Restrictions.eq("actor.id", user.getId()));
    }

    @Override
    public List<ChanceAction> limitedChanceActionList(int start, int pageSize) {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return chanceActionDao.limitedList(user, start, pageSize);
    }

    @Override
    public int limitedChanceActionRangeListCount(Date sbt, Date set) {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return chanceActionDao.limitedSearchListCount(user, sbt, set);
    }

    @Override
    public List<ChanceAction> limitedChanceActionRangeList(Date sbt, Date set, int start, int pageSize) {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return chanceActionDao.limitedSearchList(user, sbt, set, start, pageSize);
    }

    @Override
    public List<ChanceAction> dateRangeActionList(Date begin, Date end) {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return chanceActionDao.dateRangeList(user, begin, end);
    }

    @Override
    public List<Chance> keywordChanceList(String keyword) {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return chanceDao.keywordList(user, keyword);
    }

    @Override
    public List<Party> keywordPartyList(String keyword) {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return partyDao.limitedKeywordList(Party.class, user, keyword);
    }

    @Override
    public int getChanceCount() {
        return chanceDao.count();
    }

    @Override
    public List<Chance> limitedChanceList(int first, int pageSize) {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return chanceDao.limitedList(user, first, pageSize);
    }

    @Override
    public int searchedChanceCount() {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return chanceDao.count(Restrictions.eq("owner.id", user.getId()));
    }

    @Override
    public List<Chance> limitedSearchChanceList(String keyword, int first, int pageSize) {
        IUserPrincipal user = SessionLoginInfo.requireCurrentUser();
        return chanceDao.limitedSearchList(user, keyword, first, pageSize);
    }

}
