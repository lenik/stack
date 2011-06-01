package com.bee32.sem.chance.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.ext.color.PinkEntity;
import com.bee32.sem.people.entity.Party;

@Entity
public class ChanceAction
        extends PinkEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 工作日志类型
     */
    boolean plan;

    /**
     * 对应客户
     */
    List<Party> parties;

    /**
     * 洽谈方式
     */
    String style;

    /**
     * 开始时间
     */
    Date beginTime;

    /**
     * 结束时间
     */
    Date endTime;

    /**
     * 拜访目的（计划）或洽谈内容（日志）
     */
    String content;

    /**
     * 产生费用明细
     */
    String spending;

    /**
     * 对应机会
     */
    Opportunity opportunity;

    /**
     * 阶段推进
     */
    String stage;

    public String getDiaryType() {
        return diaryType;
    }

    public void setDiaryType(String diaryType) {
        this.diaryType = diaryType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Party> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Party> customers) {
        this.customers = customers;
    }

    @Lob
    public String getCostDetail() {
        return costDetail;
    }

    public void setCostDetail(String costDetail) {
        this.costDetail = costDetail;
    }

    @Column(length = 10)
    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    @Lob
    public String getDiscussContent() {
        return discussContent;
    }

    public void setDiscussContent(String discussContent) {
        this.discussContent = discussContent;
    }

    @Column(length = 200)
    public String getVisitTarget() {
        return visitTarget;
    }

    public void setVisitTarget(String visitTarget) {
        this.visitTarget = visitTarget;
    }

    @ManyToOne(optional = true)
    public SalesChance getSalesChance() {
        return salesChance;
    }

    @Column(length = 10)
    public void setSalesChance(SalesChance salesChance) {
        this.salesChance = salesChance;
    }

    public String getChanceStage() {
        return stage;
    }

    public void setChanceStage(String chanceStage) {
        this.stage = chanceStage;
    }
}
