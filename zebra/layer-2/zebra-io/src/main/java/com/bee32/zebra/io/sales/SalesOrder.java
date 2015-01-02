package com.bee32.zebra.io.sales;

import java.util.Date;
import java.util.List;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;
import com.tinylily.model.base.schema.CategoryDef;
import com.tinylily.model.base.schema.PhaseDef;
import com.tinylily.model.mx.base.CoMessage;

/**
 * op: 销售员/经办人
 * 
 * owner: 制单
 */
public class SalesOrder
        extends CoMessage {

    private static final long serialVersionUID = 1L;

    private Topic topic;
    private Organization org;
    private Person person;

    private CategoryDef category;
    private PhaseDef phase;
    private double quantity;
    private double total;

    private List<SalesOrderItem> items;

    // make-tasks
    // material-plans (locks)
    // deliveries 送货单/分次

    /**
     * 項目
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
     * 联系人
     */
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 类别
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

    /**
     * 下单时间
     */
    public Date getOrderTime() {
        return super.getBeginDate();
    }

    public void setOrderTime(Date orderTime) {
        super.setBeginDate(orderTime);
    }

    /**
     * 交货期限
     */
    public Date getDeadline() {
        return super.getEndDate();
    }

    public void setDeadline(Date deadline) {
        super.setEndDate(deadline);
    }

    /**
     * 明细列表
     */
    public List<SalesOrderItem> getItems() {
        return items;
    }

    public void setItems(List<SalesOrderItem> items) {
        this.items = items;
    }

}
