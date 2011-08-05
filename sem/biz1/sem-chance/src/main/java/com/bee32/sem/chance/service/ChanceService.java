package com.bee32.sem.chance.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.chance.dao.ChanceActionDao;
import com.bee32.sem.chance.dao.ChanceDao;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.misc.EntityCriteria;
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
    ChanceDao chanceDao;

    @Inject
    PartyDao<Party> partyDao;

    @Transactional(readOnly = false)
    public boolean unRelatingChance(Chance chance) {
        List<ChanceAction> actions = asFor(ChanceAction.class).list(EntityCriteria.ownedByCurrentUser());

        // XXX - ???
        List<ChanceAction> tempList = new ArrayList<ChanceAction>();
        for (ChanceAction action : actions) {
//            action.setChance(null);
            tempList.add(action);
        }
        return false;
    }

}
