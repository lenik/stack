package com.bee32.icsf.access.alt;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.bee32.icsf.access.acl.ACE;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityBean;

@Entity
@Table
public class ACLBean
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    Principal principal;
    Set<ACE> aceSet;

}
