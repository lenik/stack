package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.ext.color.GreenEntity;

@Entity
public class Chance
        extends GreenEntity<Long> {

    private static final long serialVersionUID = 1L;

    private String category;
    private String title;
    private String source;
    private String content;
    private String status;
    private User responsible;
    private Date createDate;

    private List<ChanceParty> parties;
    private List<ChanceHistory> histories;

    public Chance() {
        details = new ArrayList<ChanceParty>();
        histories = new ArrayList<ChanceHistory>();
    }

    /**
     * 类型
     */
    @Column(length = 20)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 机会主题
     */
    @Column(length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 来源
     */
    @Column(length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 机会内容
     */
    @Column(length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 状态
     */
    @Column(length = 20)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 负责人
     */
    @ManyToOne
    public Employee getResponsible() {
        return responsible;
    }

    public void setResponsible(Employee responsible) {
        this.responsible = responsible;
    }

    /**
     * 发现时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @OneToMany(mappedBy = "salesChance")
    public List<ChanceParty> getDetails() {
        return details;
    }

    public void setDetails(List<ChanceParty> details) {
        this.details = details;
    }

    @OneToMany(mappedBy = "salesChance")
    public List<ChanceHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<ChanceHistory> histories) {
        this.histories = histories;
    }

}
