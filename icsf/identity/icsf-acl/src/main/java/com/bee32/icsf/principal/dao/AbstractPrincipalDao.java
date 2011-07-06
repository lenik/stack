package com.bee32.icsf.principal.dao;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityDao;

public class AbstractPrincipalDao<E extends Principal>
        extends EntityDao<E, String> {

}
