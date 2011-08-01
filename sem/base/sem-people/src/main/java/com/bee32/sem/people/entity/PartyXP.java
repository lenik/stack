package com.bee32.sem.people.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.ext.xp.XPool40;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "party_xp_seq", allocationSize = 1)
public class PartyXP
        extends XPool40<Party> {

    private static final long serialVersionUID = 1L;

}
