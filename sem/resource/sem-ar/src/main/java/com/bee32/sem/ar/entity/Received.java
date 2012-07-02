package com.bee32.sem.ar.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.process.base.ProcessEntity;

//收款单
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "received_seq", allocationSize = 1)
public class Received extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    Date date;
    Party customer;
    BigDecimal amount;

    OrgUnit orgUnit;    //部门
    Person person;  //业务员
}
