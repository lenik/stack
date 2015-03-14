package com.bee32.zebra.io.sales;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.meta.cache.Derived;
import net.bodz.bas.meta.cache.Statistics;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;
import com.tinylily.model.base.CoObject;
import com.tinylily.model.base.IdType;
import com.tinylily.model.base.TableDefaults;
import com.tinylily.model.mx.base.CoMessage;

/**
 * 订单
 * 
 * op: 销售员/经办人
 * 
 * owner: 制单
 */
@IdType(Integer.class)
@Table(name = "sdoc")
@TableDefaults(accessMode = CoObject.M_COOP)
public class SalesOrder
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

    private Topic topic;
    private Organization org;
    private Person person;

    private double quantity;
    private double total;

    private List<SalesOrderItem> items;

    // make-tasks
    // material-plans (locks)
    // deliveries 送货单/分次

    @Override
    public void instantiate() {
        super.instantiate();
        items = new ArrayList<>();
    }

    /**
     * 項目
     */
    @OfGroup(StdGroup.Classification.class)
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
     * 总数量
     */
    @OfGroup(StdGroup.Statistics.class)
    @Statistics
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * 总金额
     */
    @OfGroup(StdGroup.Statistics.class)
    @Statistics
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Derived
    @Override
    public Date getBeginDate() {
        return super.getBeginDate();
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Derived
    @Override
    public Date getEndDate() {
        return super.getEndDate();
    }

    /**
     * 下单时间
     */
    @OfGroup(StdGroup.Schedule.class)
    public Date getOrderTime() {
        return super.getBeginDate();
    }

    public void setOrderTime(Date orderTime) {
        super.setBeginDate(orderTime);
    }

    /**
     * 交货期限
     */
    @OfGroup(StdGroup.Schedule.class)
    public Date getDeadline() {
        return super.getEndDate();
    }

    public void setDeadline(Date deadline) {
        super.setEndDate(deadline);
    }

    /**
     * 明细列表
     */
    @DetailLevel(DetailLevel.EXTEND)
    public List<SalesOrderItem> getItems() {
        return items;
    }

    public void setItems(List<SalesOrderItem> items) {
        this.items = items;
    }

}
