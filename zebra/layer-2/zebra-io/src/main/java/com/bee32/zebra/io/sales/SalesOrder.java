package com.bee32.zebra.io.sales;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.joda.time.DateTime;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.meta.cache.Derived;
import net.bodz.bas.meta.cache.Statistics;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.mx.base.CoMessage;

import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;

/**
 * 订单
 * 
 * op: 销售员/经办人
 * 
 * owner: 制单
 */
@IdType(Integer.class)
@Table(name = "sdoc")
public class SalesOrder
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

    private Topic topic;
    private Organization org;
    private Person person;

    private List<SalesOrderItem> items;
    private int itemCount = SIZE_UNKNOWN;
    private double quantity;
    private double total;

    // make-tasks
    // material-plans (locks)

    private List<Delivery> deliveries;
    private int deliveryCount = SIZE_UNKNOWN;

    @Override
    public void instantiate() {
        super.instantiate();
        setAccessMode(M_COOP);
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
    public DateTime getBeginDate() {
        return super.getBeginDate();
    }

    @DetailLevel(DetailLevel.HIDDEN)
    @Derived
    @Override
    public DateTime getEndDate() {
        return super.getEndDate();
    }

    /**
     * 下单时间
     */
    @OfGroup(StdGroup.Schedule.class)
    public DateTime getOrderTime() {
        return super.getBeginDate();
    }

    public void setOrderTime(DateTime orderTime) {
        super.setBeginDate(orderTime);
    }

    /**
     * 交货期限
     */
    @OfGroup(StdGroup.Schedule.class)
    public DateTime getDeadline() {
        return super.getEndDate();
    }

    public void setDeadline(DateTime deadline) {
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

    /**
     * 明细条数
     */
    @DetailLevel(DetailLevel.EXTEND)
    public int getItemCount() {
        return SizeFn.getSize(items, itemCount);
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    /**
     * 送货跟踪
     */
    @DetailLevel(DetailLevel.EXTEND)
    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    /**
     * 送货单数（张）
     */
    @DetailLevel(DetailLevel.EXTEND)
    public int getDeliveryCount() {
        return SizeFn.getSize(deliveries, deliveryCount);
    }

    public void setDeliveryCount(int deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

}
