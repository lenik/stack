package com.bee32.sem.calendar.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.plover.orm.ext.color.PinkEntity;
import com.bee32.sem.customer.entity.SalesChance;

public class WorkDiary
        extends PinkEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 工作日志类型
     */
    private String diaryType;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 对应客户
     */
    private List<Party> customers;

    /**
     * 产生费用明细
     */
    private String costDetail;

    /**
     * 联系类型
     */
    private String contactType;

    /**
     * 洽谈内容描述
     */
    private String discussContent;

    /**
     * 拜访目的
     */
    private String visitTarget;

    /**
     * 对应机会
     */
    private SalesChance salesChance;

    /**
     * 机会阶段
     */
    private String chanceStage;

    public WorkDiary() {
        super();
    }

    public WorkDiary(String diaryType, Date beginTime, Date endTime, List<Party> customers, String costDetail,
            String contactType, String discussContent, String visitTarget, SalesChance salesChance, String chanceStage) {
        super();
        this.diaryType = diaryType;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.customers = customers;
        this.costDetail = costDetail;
        this.contactType = contactType;
        this.discussContent = discussContent;
        this.visitTarget = visitTarget;
        this.salesChance = salesChance;
        this.chanceStage = chanceStage;
    }

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

    @ManyToOne(optional = true )
    public SalesChance getSalesChance() {
        return salesChance;
    }

    @Column(length = 10)
    public void setSalesChance(SalesChance salesChance) {
        this.salesChance = salesChance;
    }

    public String getChanceStage() {
        return chanceStage;
    }

    public void setChanceStage(String chanceStage) {
        this.chanceStage = chanceStage;
    }
}
