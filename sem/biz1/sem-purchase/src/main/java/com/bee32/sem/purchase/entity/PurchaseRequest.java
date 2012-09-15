package com.bee32.sem.purchase.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.sem.makebiz.entity.MaterialPlan;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 采购请求
 *
 * 采购申请,采购计划。对应物料计划。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_request_seq", allocationSize = 1)
public class PurchaseRequest
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    List<MaterialPlan> plans = new ArrayList<MaterialPlan>();
    List<PurchaseRequestItem> items = new ArrayList<PurchaseRequestItem>();
    List<PurchaseTakeIn> takeIns = new ArrayList<PurchaseTakeIn>();

    @Override
    public void populate(Object source) {
        if (source instanceof PurchaseRequest)
            _populate((PurchaseRequest) source);
        else
            super.populate(source);
    }

    protected void _populate(PurchaseRequest o) {
        super._populate(o);
        plans = new ArrayList<MaterialPlan>(o.plans);
        items = CopyUtils.copyList(o.items);
        takeIns = CopyUtils.copyList(o.takeIns);
    }

    /**
     * 物料计划列表
     *
     * 本采购请求对应的物料计划列表。
     *
     * @return
     */
    @OneToMany
    @JoinTable(name = "PurchaseRequestPlan",//
    /*            */joinColumns = @JoinColumn(name = "request"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "plan"))
    public List<MaterialPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<MaterialPlan> plans) {
        if (plans == null)
            throw new NullPointerException("plans");
        this.plans = plans;
    }

    /**
     * 采购计划明细
     *
     * 采购计划明细列表。
     *
     * @return
     */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PurchaseRequestItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseRequestItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(PurchaseRequestItem item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(PurchaseRequestItem item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        item.detach();

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

    /**
     * 采购总数量
     *
     * 本采购请求对应的总数量。
     */
    @Transient
    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        for (PurchaseRequestItem item : items) {
            total = total.add(item.getQuantity());
        }
        return total;
    }

    /**
     * 采购入库单
     *
     * 采购请求在询价完成后，直接可以进入仓库，此处为对应的入库单列表。
     */
    @OneToMany(mappedBy = "purchaseRequest", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PurchaseTakeIn> getTakeIns() {
        return takeIns;
    }

    public void setTakeIns(List<PurchaseTakeIn> takeIns) {
        if (takeIns == null)
            throw new NullPointerException("takeIns");
        this.takeIns = takeIns;
    }

    /**
     * 数量含义
     *
     * 数量对应的文字含义。
     */
    @Transient
    @Override
    public String getNumberDescription() {
        return "总数量";
    }

    @Override
    protected Number computeJudgeNumber()
            throws Exception {
        return getTotal();
    }

}
