package com.bee32.sem.people.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.xp.XPool40;

/**
 * 公司/人员属性池
 *
 * 用于扩展涉众的信息。
 * <p lang="en">
 * Party Attributes Pool
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "partyxp_seq", allocationSize = 1)
public class PartyXP
        extends XPool40<Party> {

    private static final long serialVersionUID = 1L;

}
