package com.bee32.zebra.io.stock;

import java.util.ArrayList;
import java.util.List;

import com.bee32.zebra.oa.contact.OrgUnit;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;
import com.tinylily.model.base.schema.CategoryDef;
import com.tinylily.model.base.schema.PhaseDef;
import com.tinylily.model.mx.base.CoMessage;

public class StockEvent
        extends CoMessage {

    private static final long serialVersionUID = 1L;

    private Topic topic;
    private Organization org;
    private OrgUnit orgUnit;
    private Person person;

    private CategoryDef category;
    private PhaseDef phase;

    private List<StockEntry> entries = new ArrayList<>();
    private double quantity;
    private double total;

    /**
     * 项目
     */
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    /**
     * 企业
     */
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    /**
     * 部门
     */
    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * 客户
     */
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 分类
     */
    public CategoryDef getCategory() {
        return category;
    }

    public void setCategory(CategoryDef category) {
        this.category = category;
    }

    /**
     * 阶段
     */
    public PhaseDef getPhase() {
        return phase;
    }

    public void setPhase(PhaseDef phase) {
        this.phase = phase;
    }

    /**
     * 项目
     */
    public List<StockEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<StockEntry> entries) {
        this.entries = entries;
    }

    /**
     * 总数量
     */
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * 总金额
     */
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public synchronized void update() {
        quantity = 0;
        total = 0;
        for (StockEntry entry : entries) {
            quantity += entry.getQuantity();
            total += entry.getTotal();
        }

    }

}
