package com.bee32.sem.ar.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;

public class Receivable {

    Date date;  //日期
    Party customer; //客户
    BigDecimal amount;  //金额

    OrgUnit orgUnit;    //部门
    Person person;  //业务员
}
