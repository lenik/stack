package com.bee32.sem.account.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 应收单，收款单，应付单，付款单基类
 *
 * 单据日期：beginTime
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 5)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "current_account_seq", allocationSize = 1)
public class CurrentAccount
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    Party party;
    MCValue amount = new MCValue();

    OrgUnit orgUnit;
    Person person;

    /**
     * 对应客户或供应商
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    /**
     * 金额
     */
    @Embedded
    @AttributeOverrides({
            // { price_c, price }
            @AttributeOverride(name = "currencyCode", column = @Column(name = "amount_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "amount")) })
    public MCValue getAmount() {
        return amount;
    }

    public void setAmount(MCValue amount) {
        this.amount = amount;
    }

    /**
     * 公司内部部门
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
     */
    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
