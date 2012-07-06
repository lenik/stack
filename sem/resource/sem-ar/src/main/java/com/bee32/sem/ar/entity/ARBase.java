package com.bee32.sem.ar.entity;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 应收单，收款单基类
 * @author jack
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "ar_base_seq", allocationSize = 1)
public class ARBase extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    Date date;
    Party customer;
    MCValue amount;

    OrgUnit orgUnit;
    Person person;

    /**
     * 单据日期
     * @return
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 对应客户
     * @return
     */
    @ManyToOne(optional = false)
    public Party getCustomer() {
        return customer;
    }

    public void setCustomer(Party customer) {
        this.customer = customer;
    }

    /**
     * 金额
     * @return
     */
    @Embedded
    @AttributeOverrides({
            // { price_c, price }
            @AttributeOverride(name = "currencyCode", column = @Column(name = "price_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "price")) })
    public MCValue getAmount() {
        return amount;
    }

    public void setAmount(MCValue amount) {
        this.amount = amount;
    }

    /**
     * 公司内部部门
     * @return
     */
    @ManyToOne
    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * 公司内部业务员
     * @return
     */
    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
