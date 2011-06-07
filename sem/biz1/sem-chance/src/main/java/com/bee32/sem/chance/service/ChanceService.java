package com.bee32.sem.chance.service;

import javax.inject.Inject;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.chance.dao.ChanceActionDao;

public class ChanceService
        extends EnterpriseService
        implements IChanceService {

    @Inject
    UserDao userDao;

    @Inject
    ChanceActionDao chanceActionDao;

}
