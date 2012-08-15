package com.bee32.sem.service;

import java.util.List;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.people.entity.Org;

public class PeopleService
        extends DataService
        implements IPeopleService {

    @Override
    public Org getSelfOrg() {
        List<Org> selfOrgs = DATA(Org.class).list(new Equals("employee", true));
        if(selfOrgs != null && selfOrgs.size() > 0) {
            return selfOrgs.get(0);
        }
        return null;
    }


}
