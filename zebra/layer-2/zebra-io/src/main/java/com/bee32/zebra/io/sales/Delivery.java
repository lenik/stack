package com.bee32.zebra.io.sales;

import java.util.Date;
import java.util.List;

import com.bee32.zebra.oa.contact.Contact;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.tinylily.model.base.security.User;
import com.tinylily.model.mx.base.CoMessage;

public class Delivery
        extends CoMessage {

    private static final long serialVersionUID = 1L;

    private SalesOrder salesOrder;

    private Organization org;
    private Person person;

    private Contact shipDest;
    private Organization shipper;
    private String shipmentId;

    // Take-Out stock job
    // Account-Ticket

    private double quantity;
    private double total;

    private List<DeliveryItem> items;

    /**
     * 订单
     */
    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    /**
     * 公司
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
     * 目的地
     */
    public Contact getShipDest() {
        return shipDest;
    }

    public void setShipDest(Contact shipDest) {
        this.shipDest = shipDest;
    }

    /**
     * 承运人
     */
    public Organization getShipper() {
        return shipper;
    }

    public void setShipper(Organization shipper) {
        this.shipper = shipper;
    }

    /**
     * 运单号
     */
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    /**
     * 发货时间
     */
    public Date getShipDate() {
        return super.getBeginDate();
    }

    public void setShipDate(Date shipDate) {
        super.setBeginDate(shipDate);
    }

    /**
     * 收货时间
     */
    public Date getArrivedDate() {
        return super.getEndDate();
    }

    public void setArrivedDate(Date arrivedDate) {
        super.setEndDate(arrivedDate);
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
     * 明细列表
     */
    public List<DeliveryItem> getItems() {
        return items;
    }

    public void setItems(List<DeliveryItem> items) {
        this.items = items;
    }

    /**
     * 经办人
     */
    @Override
    // @OfGroup(OaGroups.UserInteraction.class)
    public User getOp() {
        return super.getOp();
    }

}
