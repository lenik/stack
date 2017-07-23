package com.bee32.zebra.io.stock;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import net.bodz.bas.meta.bean.DetailLevel;
import net.bodz.bas.meta.cache.Statistics;
import net.bodz.bas.repr.form.meta.OfGroup;
import net.bodz.bas.repr.form.meta.StdGroup;
import net.bodz.lily.entity.IdType;
import net.bodz.lily.model.mx.CoMessage;
import net.bodz.violet.store.StoreCategory;
import net.bodz.violet.store.StorePhase;

import com.bee32.zebra.oa.contact.OrgUnit;
import com.bee32.zebra.oa.contact.Organization;
import com.bee32.zebra.oa.contact.Person;
import com.bee32.zebra.oa.thread.Topic;

/**
 * 库存作业
 */
@IdType(Integer.class)
// @SchemaPref(Schemas.STOCK)
@Table(name = "stdoc")
public class StockEvent
        extends CoMessage<Integer> {

    private static final long serialVersionUID = 1L;

    private StockEvent previous;
    private StoreCategory category;
    private StorePhase phase;
    private Topic topic;
    private Organization org;
    private OrgUnit orgUnit;
    private Person person;

    private List<StockEntry> entries = new ArrayList<>();
    private int entryCount = SIZE_UNKNOWN;
    private double quantity;
    private double total;

    public StockEvent() {
    }

    @Override
    public void instantiate() {
        super.instantiate();
        setAccessMode(M_SHARED);

        StoreCategory TK_I = new StoreCategory();
        TK_I.setId(1202);
        TK_I.setLabel("采购入库");
        setCategory(TK_I);
    }

    /**
     * 前级
     */
    @OfGroup(StdGroup.Process.class)
    public StockEvent getPrevious() {
        return previous;
    }

    public void setPrevious(StockEvent previous) {
        this.previous = previous;
    }

    public StoreCategory getCategory() {
        return category;
    }

    public void setCategory(StoreCategory category) {
        this.category = category;
    }

    public StorePhase getPhase() {
        return phase;
    }

    public void setPhase(StorePhase phase) {
        this.phase = phase;
    }

    /**
     * 项目/机会
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
     * 明细
     */
    @DetailLevel(DetailLevel.EXTEND)
    public List<StockEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<StockEntry> entries) {
        this.entries = entries;
    }

    /**
     * 明细条数
     */
    @DetailLevel(DetailLevel.EXTEND)
    public int getEntryCount() {
        return SizeFn.getSize(entries, entryCount);
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
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

    public synchronized void update() {
        quantity = 0;
        total = 0;
        for (StockEntry entry : entries) {
            quantity += entry.getQuantity();
            total += entry.getTotal();
        }

    }

}
