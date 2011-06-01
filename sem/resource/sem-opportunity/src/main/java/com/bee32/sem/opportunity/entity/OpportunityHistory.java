package com.bee32.sem.opportunity.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.ext.color.BlueEntity;
import com.bee32.sem.org.entity.Employee;

@Entity
public class OpportunityHistory
        extends BlueEntity<Long> {

    private static final long serialVersionUID = 1L;

    private String category;
    private String subject;
    private String description;
    private String chanceStatus;
    private Opportunity salesChance;
    private Employee enforcer;
    private Date date;

    public OpportunityHistory() {
    }

    @Column(length = 30)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(length = 100)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(length = 100)
    public String getChanceStatus() {
        return chanceStatus;
    }

    public void setChanceStatus(String chanceStatus) {
        this.chanceStatus = chanceStatus;
    }

    @ManyToOne
    public Opportunity getSalesChance() {
        return salesChance;
    }

    public void setSalesChance(Opportunity salesChance) {
        this.salesChance = salesChance;
    }

    @ManyToOne
    public Employee getEnforcer() {
        return enforcer;
    }

    public void setEnforcer(Employee enforcer) {
        this.enforcer = enforcer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
