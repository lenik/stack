package com.bee32.sem.ar.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 应收单
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "receivable_seq", allocationSize = 1)
public class Receivable extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    Date date;  //日期
    Party customer; //客户
    BigDecimal amount;  //金额

    OrgUnit orgUnit;    //部门
    Person person;  //业务员

}
